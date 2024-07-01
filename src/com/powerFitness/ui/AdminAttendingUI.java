package com.powerFitness.ui;

import com.powerFitness.controller.AttendanceController;
import com.powerFitness.dto.AttendanceDTO;
import com.powerFitness.dto.UserDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdminAttendingUI extends JDialog {
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton clearButton;
    private JTable table;
    private DefaultTableModel model;
    private AttendanceController attendanceController;

    /**
     * 관리자 수강 관리 UI 생성자
     * @param owner 부모 프레임
     * @param currentUser 현재 사용자 정보
     * @param modal 모달 여부
     */
    public AdminAttendingUI(Frame owner, UserDTO currentUser, boolean modal) {
        super(owner, "수강 관리", modal);
        this.attendanceController = new AttendanceController(currentUser);
        initializeUI();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
    }

    /**
     * UI 초기화
     */
    private void initializeUI() {
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Input Panel Setup
        JPanel inputPanel = new JPanel();
        searchTextField = new JTextField(20);
        inputPanel.add(searchTextField);

        // Search Button
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        inputPanel.add(searchButton);

        // Clear Button
        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearSearch());
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table Model and JTable
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"수강ID", "회원ID", "회원이름", "수업ID", "수업이름", "결제ID", "수강일자", "수강상태"});
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(550, 300));
        table.setFillsViewportHeight(true);

        // Scroll Pane for Table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * 검색 필드 초기화
     */
    private void clearSearch() {
        searchTextField.setText("");
        model.setRowCount(0);
    }

    /**
     * 검색 수행
     */
    private void performSearch() {
        String searchText = searchTextField.getText().trim();
        try {
            List<AttendanceDTO> attendances;
            if (!searchText.isEmpty()) {
                attendances = attendanceController.fetchEnrollmentsByMemberName(searchText);
            } else {
                attendances = attendanceController.fetchAllAttendances();
            }
            displayAttendances(attendances);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 수강 정보 표시
     * @param attendances 수강 정보 목록
     */
    private void displayAttendances(List<AttendanceDTO> attendances) {
        model.setRowCount(0); // Clear previous entries
        for (AttendanceDTO attendance : attendances) {
            model.addRow(new Object[]{
                attendance.getAttendId(),
                attendance.getMemberId(),
                attendance.getMemberName(),
                attendance.getClassId(),
                attendance.getClassName(),
                attendance.getPayId(),
                attendance.getDate(),
                attendance.getStatus()
            });
        }
    }
}
