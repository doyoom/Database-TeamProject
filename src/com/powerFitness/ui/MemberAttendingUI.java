package com.powerFitness.ui;

import com.powerFitness.controller.*;
import com.powerFitness.dto.AttendanceDTO;
import com.powerFitness.dto.UserDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * MemberAttendingUI 클래스는 회원의 수강 내역을 표시하고 수강 취소 기능을 제공하는 사용자 인터페이스를 제공합니다.
 */
public class MemberAttendingUI extends JDialog {
    private AttendanceController attendanceController;
    private EnrollmentController enrollmentController;
    private JTable table;
    private DefaultTableModel model;

    /**
     * MemberAttendingUI 생성자.
     *
     * @param owner  부모 Window
     * @param user   현재 사용자 정보를 담은 UserDTO 객체
     * @param modal  모달 여부
     */
    public MemberAttendingUI(Window owner, UserDTO user, boolean modal) {
        super(owner, "내 수강 내역", Dialog.ModalityType.APPLICATION_MODAL); // 모달 설정 추가
        this.attendanceController = new AttendanceController(user);
        this.enrollmentController = new EnrollmentController(user);
        initializeUI();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // 변경: JFrame에서 JDialog 변경
        setLocationRelativeTo(owner); // 상대 위치 설정
    }

    /**
     * 사용자 인터페이스를 초기화합니다.
     */
    private void initializeUI() {
        setTitle("내 수강 내역");
        setSize(600, 400);
        setLayout(new BorderLayout());

        // 테이블과 모델 초기화
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"수강ID", "수업이름", "수강일자", "수강상태", "결제상태"});
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(550, 300));
        table.setFillsViewportHeight(true);

        // 스크롤 패널과 테이블을 중앙에 추가
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("수강 내역 조회");
        JButton cancelButton = new JButton("수강 취소");
        buttonPanel.add(refreshButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadMemberAttendances());
        cancelButton.addActionListener(e -> cancelEnrollment());
    }

    /**
     * 회원의 수강 내역을 로드합니다.
     */
    private void loadMemberAttendances() {
        if (!attendanceController.getCurrentUser().getRole().equals("member")) {
            JOptionPane.showMessageDialog(this, "접근 권한이 없습니다. 이 기능은 회원만 이용 가능합니다.", "접근 거부", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            List<AttendanceDTO> attendances = attendanceController.fetchMemberAttendances();
            displayAttendances(attendances);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "수강 내역을 가져오는 중 오류가 발생했습니다: " + e.getMessage(), "데이터베이스 오류", JOptionPane.ERROR_MESSAGE);
        } catch (SecurityException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "보안 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 수강 내역을 화면에 표시합니다.
     *
     * @param attendances 수강 내역 리스트
     */
    private void displayAttendances(List<AttendanceDTO> attendances) {
        model.setRowCount(0); // 이전 항목 지우기
        if (attendances.isEmpty()) {
            JOptionPane.showMessageDialog(this, "수강 내역이 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (AttendanceDTO attendance : attendances) {
                model.addRow(new Object[]{
                    attendance.getAttendId(),
                    attendance.getClassName() != null ? attendance.getClassName() : "-",
                    attendance.getDate() != null ? attendance.getDate() : "-",
                    attendance.getStatus() != null ? attendance.getStatus() : "-",
                    attendance.getPaymentStatus() != null ? attendance.getPaymentStatus() : "-"
                });
            }
        }
    }

    /**
     * 선택된 수강 내역을 취소합니다.
     */
    private void cancelEnrollment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "수강을 선택하세요.");
            return;
        }

        String attendId = table.getValueAt(selectedRow, 0).toString(); // 수강 ID를 가져옵니다.

        try {
            // AttendanceDTO 리스트에서 attendId에 해당하는 객체를 찾습니다.
            AttendanceDTO selectedAttendance = attendanceController.fetchMemberAttendances().stream()
                .filter(attendance -> attendance.getAttendId().equals(attendId))
                .findFirst()
                .orElse(null);

            if (selectedAttendance == null) {
                JOptionPane.showMessageDialog(this, "해당 수강 정보를 찾을 수 없습니다.");
                return;
            }

            String payId = selectedAttendance.getPayId(); // 찾은 객체에서 payId를 추출합니다.
            String paymentStatus = selectedAttendance.getPaymentStatus(); // 결제 상태도 가져올 수 있습니다.

            boolean success = false;
            if (paymentStatus.equals("결제미완료")) {
                success = enrollmentController.cancelEnrollmentWithoutPayment(attendId, payId);
            } else if (paymentStatus.equals("결제완료")) {
                success = enrollmentController.cancelEnrollmentWithPayment(attendId, payId);
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "수강 취소가 완료되었습니다.");
                loadMemberAttendances(); // 테이블 새로 고침
            } else {
                JOptionPane.showMessageDialog(this, "수강 취소 실패.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "오류 발생: " + ex.getMessage());
        }
    }
}
