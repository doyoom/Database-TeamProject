package com.powerFitness.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.powerFitness.controller.*;
import com.powerFitness.dto.PaymentDTO;
import com.powerFitness.dto.UserDTO;

/**
 * 관리자용 결제 관리 UI.
 */
public class AdminPaymentUI extends JDialog {
    private JTextField searchTextField;
    private JCheckBox statusComplete;
    private JCheckBox statusCancelled;
    private JCheckBox statusPending;
    private JButton searchButton;
    private JButton clearButton; 
    private JTable table;
    private DefaultTableModel model;
    private PaymentController paymentController;
    private EnrollmentController enrollmentController;

    /**
     * AdminPaymentUI 생성자.
     * 
     * @param owner       부모 프레임
     * @param currentUser 현재 사용자 정보
     * @param modal       모달 여부
     */
    public AdminPaymentUI(Frame owner, UserDTO currentUser, boolean modal) {
        super(owner, "결제 관리", modal);
        this.paymentController = new PaymentController(currentUser);
        this.enrollmentController = new EnrollmentController(currentUser);
        
        initializeUI();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
    }

    /**
     * UI 초기화 메소드.
     */
    private void initializeUI() {
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        searchTextField = new JTextField(10);
        inputPanel.add(searchTextField);

        statusComplete = new JCheckBox("결제완료");
        statusCancelled = new JCheckBox("결제취소");
        statusPending = new JCheckBox("결제미완료");
        inputPanel.add(statusComplete);
        inputPanel.add(statusCancelled);
        inputPanel.add(statusPending);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        inputPanel.add(searchButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearSearch());
        inputPanel.add(clearButton);
        
        JButton approveButton = new JButton("결제 승인");
        approveButton.addActionListener(new ApprovePaymentActionListener());
        inputPanel.add(approveButton);

        JButton cancelButton = new JButton("결제 취소");
        cancelButton.addActionListener(new CancelPaymentActionListener());
        inputPanel.add(cancelButton);


        add(inputPanel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"결제ID", "회원ID", "회원이름", "결제수단", "결제금액", "결제일자", "결제상태"});
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(550, 300));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

    }

    /**
     * 검색 내용 초기화 메소드.
     */
    private void clearSearch() {
        searchTextField.setText("");
        statusComplete.setSelected(false);
        statusCancelled.setSelected(false);
        statusPending.setSelected(false);
        model.setRowCount(0); // Clear the table
    }

    /**
     * 검색 수행 메소드.
     */
    private void performSearch() {
    	
    	model.setRowCount(0);
    	
    	System.out.println("Performing search..."); // 검색 시작 로그
    	
        String memberName = searchTextField.getText().trim(); // 이름 입력값, 공백 제거
        System.out.println("Searching for: " + memberName); // 검색어 로그
        
        String status = getStatusSelected(); // 결제 상태 선택
        System.out.println("Status selected: " + status); // 선택된 상태 로그
        
        new SwingWorker<List<PaymentDTO>, Void>() {
            @Override
            protected List<PaymentDTO> doInBackground() throws Exception {
                if (!memberName.isEmpty() && !status.isEmpty()) {
                    return paymentController.fetchFilteredPaymentsByStatusAndName(status, memberName);
                } else if (!memberName.isEmpty()) {
                    return paymentController.fetchPaymentsByMemberName(memberName);
                } else if (!status.isEmpty()) {
                    return paymentController.fetchPaymentsByStatus(status);
                } else {
                    return paymentController.fetchAllPayments();
                }
            }

            @Override
            protected void done() {
                try {
                    List<PaymentDTO> payments = get(); // This can throw the checked exceptions
                    displayPayments(payments);
                    table.revalidate(); // Ensure the table updates visually
                    table.repaint();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace(); // Log to console for deeper debugging
                } finally {
                    // You could add any additional cleanup or UI reset tasks here
                }
            }
            
        }.execute();
    }

    /**
     * 선택된 결제 상태 가져오는 메소드.
     *
     * @return 선택된 결제 상태 문자열
     */
    private String getStatusSelected() {
        if (statusComplete.isSelected()) return "결제완료";
        if (statusCancelled.isSelected()) return "결제취소";
        if (statusPending.isSelected()) return "결제미완료";
        return ""; // or handle default/no selection case
    }

    /**
     * 결제 내역을 테이블에 표시하는 메소드.
     *
     * @param payments 결제 내역 리스트
     */
    private void displayPayments(List<PaymentDTO> payments) {
        model.setRowCount(0); // Clear existing data
        if (payments.isEmpty()) {
            // 결과가 없을 때 사용자에게 메시지를 보여주는 방법
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (PaymentDTO payment : payments) {
                Object[] rowData = {
                    payment.getPayId(),
                    payment.getMemberId() != null ? payment.getMemberId() : "-",
                    payment.getMemberName() != null ? payment.getMemberName() : "-",
                    payment.getPayType() != null ? payment.getPayType() : "미정",
                    payment.getPayFee(),
                    payment.getDate() != null ? payment.getDate() : "-",
                    payment.getStatus() != null ? payment.getStatus() : "-"
                };
                model.addRow(rowData);
            }
        }
    }
    
    /**
     * 결제 승인 액션 리스너 내부 클래스.
     */
    private class ApprovePaymentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String payId = table.getValueAt(selectedRow, 0).toString();
                String paymentStatus = table.getValueAt(selectedRow, 6).toString(); // 결제 상태 컬럼이 6번째라고 가정

                // 결제 상태가 "결제미완료"일 때만 승인 진행
                if ("결제미완료".equals(paymentStatus)) {
                    // 결제 방식 선택을 위한 대화상자 생성
                    JComboBox<String> payTypeSelector = new JComboBox<>(new String[]{"카드", "현금"});
                    int result = JOptionPane.showConfirmDialog(null, payTypeSelector, "결제 방식 선택", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    
                    if (result == JOptionPane.OK_OPTION) {
                        String payType = (String) payTypeSelector.getSelectedItem();
                        // 현재 날짜를 java.sql.Date 타입으로 생성
                        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                        
                        try {                            // 현재 날짜 객체를 사용하여 결제 승인
                            boolean success = enrollmentController.approvePayment(payId, payType, currentDate);
                            if (success) {
                                JOptionPane.showMessageDialog(null, "결제가 승인되었습니다.");
                                performSearch(); // Refresh the table
                            } else {
                                JOptionPane.showMessageDialog(null, "결제 승인 실패.");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "오류: " + ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "결제 승인이 취소되었습니다.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "이미 완료되었거나 취소된 결제는 승인할 수 없습니다.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "결제를 선택하세요.");
            }
        }
    }


    
    /**
     * 결제 취소 액션 리스너 내부 클래스.
     */
    private class CancelPaymentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
            	String payId = table.getValueAt(selectedRow, 0).toString();
                String currentStatus = table.getValueAt(selectedRow, 6).toString(); // 결제상태 컬럼

                // "결제미완료" 상태의 결제는 취소 불가능
                if ("결제미완료".equals(currentStatus)) {
                    JOptionPane.showMessageDialog(null, "결제미완료 상태의 결제는 취소할 수 없습니다.", "결제 취소 불가", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        boolean success = enrollmentController.cancelPayment(payId);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "결제가 취소되었습니다.");
                            performSearch(); // 테이블을 새로고침하여 변경사항 반영
                        } else {
                            JOptionPane.showMessageDialog(null, "결제 취소 실패.");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "오류: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "결제를 선택하세요.");
            }
        }
    }

}

                           
