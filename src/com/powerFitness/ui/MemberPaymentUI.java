package com.powerFitness.ui;

import com.powerFitness.controller.PaymentController;
import com.powerFitness.dto.PaymentDTO;
import com.powerFitness.dto.UserDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * MemberPaymentUI 클래스는 회원의 결제 내역을 표시하는 사용자 인터페이스를 제공합니다.
 */
public class MemberPaymentUI extends JDialog {
    private PaymentController paymentController;
    private JTable table;
    private DefaultTableModel model;

    /**
     * MemberPaymentUI 생성자.
     *
     * @param owner  부모 Window
     * @param user   현재 사용자 정보를 담은 UserDTO 객체
     * @param modal  모달 여부
     */
    public MemberPaymentUI(Window owner, UserDTO user, boolean modal) {
        super(owner, "내 결제 내역", Dialog.ModalityType.APPLICATION_MODAL); // 모달 설정을 APPLICATION_MODAL로 명시적 설정
        this.paymentController = new PaymentController(user);
        initializeUI();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // JDialog의 기본 동작 설정
        setLocationRelativeTo(owner); // 부모 윈도우에 상대적으로 위치 설정
    }

    /**
     * 사용자 인터페이스를 초기화합니다.
     */
    private void initializeUI() {
        setTitle("내 결제 내역");
        setSize(600, 400); // 테이블 표시를 위한 크기 조정
        setLayout(new BorderLayout());

        // 테이블과 모델 초기화
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"결제ID", "결제수단", "결제금액", "결제일자", "결제상태"});
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(550, 300));
        table.setFillsViewportHeight(true);

        // 스크롤 패널과 테이블을 중앙에 추가
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 결제 내역 조회 버튼 추가
        JButton refreshButton = new JButton("결제 내역 조회");
        refreshButton.addActionListener(e -> loadMemberPayments());
        add(refreshButton, BorderLayout.SOUTH);
    }

    /**
     * 회원의 결제 내역을 로드합니다.
     */
    private void loadMemberPayments() {
        if (!paymentController.getCurrentUser().getRole().equals("member")) {
            JOptionPane.showMessageDialog(this, "Access denied. You are not allowed to view these details.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            List<PaymentDTO> payments = paymentController.fetchMemberPayments();
            displayPayments(payments);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving payments: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (SecurityException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Security Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 결제 내역을 화면에 표시합니다.
     *
     * @param payments 결제 내역 리스트
     */
    private void displayPayments(List<PaymentDTO> payments) {
        model.setRowCount(0); // 이전 항목 지우기
        if (payments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "결제 내역이 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (PaymentDTO payment : payments) {
                model.addRow(new Object[]{
                    payment.getPayId(),
                    payment.getPayType() != null ? payment.getPayType() : "-",
                    payment.getPayFee(),
                    payment.getDate() != null ? payment.getDate() : "-",
                    payment.getStatus() != null ? payment.getStatus() : "-"
                });
            }
        }
    }
}
