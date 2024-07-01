package com.powerFitness.ui;

import com.powerFitness.MainFrame;
import com.powerFitness.controller.CenterController;
import com.powerFitness.dto.CenterDTO;
import com.powerFitness.dto.UserDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 센터 정보를 표시하고 관리하는 UI 클래스입니다.
 */
public class CenterUI extends JFrame {

    private CenterController centerController;
    private JTable centerTable;
    private JLabel userLabel;
    private JButton loginLogoutButton;
    private JButton detailButton;
    private JButton updateButton;
    private UserDTO loggedInUser;
    private MainFrame mainFrame;
    private JButton returnToMainButton;

    /**
     * 기본 생성자
     */
    public CenterUI() {
        centerController = new CenterController();
        initUI();
        displayCenters();
    }

    /**
     * 사용자 정보가 있는 생성자
     * @param loggedInUser 로그인한 사용자 정보
     */
    public CenterUI(UserDTO loggedInUser) {
        this.loggedInUser = loggedInUser;
        centerController = new CenterController();
        initUI();
        displayCenters();

    }

    /**
     * 사용자 정보와 메인 프레임이 있는 생성자
     * @param loggedInUser 로그인한 사용자 정보
     * @param mainFrame 메인 프레임
     */
    public CenterUI(UserDTO loggedInUser, MainFrame mainFrame ) {
        this.loggedInUser = loggedInUser;
        this.mainFrame = mainFrame;
        centerController = new CenterController(); 
        initUI();
        displayCenters();
    }

    /**
     * UI를 초기화합니다.
     */
    private void initUI() {
        setTitle("Power Fitness - 센터 목록");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String userInfo = loggedInUser != null ? "현재 사용자: " + loggedInUser.getName() + " (" + loggedInUser.getRole() + ")" : "현재 사용자: 게스트";
        userLabel = new JLabel(userInfo);
        userPanel.add(userLabel);

        JPanel buttonPanel = new JPanel();
        detailButton = new JButton("상세설명");
        updateButton = new JButton("수정");
        detailButton.addActionListener(new DetailActionListener());
        updateButton.addActionListener(new UpdateActionListener());
        buttonPanel.add(detailButton);
        buttonPanel.add(updateButton);
        
        //로그인 버튼 추가 
        loginLogoutButton = new JButton();
        updateLoginLogoutButton();
        userPanel.add(loginLogoutButton);
        
        //메인페이지로 돌아가는 버튼 추가 
        returnToMainButton = new JButton("메인 화면");
        if (mainFrame!=null) {
        	
        	
            returnToMainButton.addActionListener(e -> {
                mainFrame.setVisible(true); 
                dispose();
                
            });
        }
        userPanel.add(returnToMainButton);
    

        centerTable = new JTable();
        centerTable.setModel(new DefaultTableModel(new Object[]{"센터명", "주소", "전화번호", "운영시간", "시설정보"}, 0));
        JScrollPane scrollPane = new JScrollPane(centerTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(userPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.WEST);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        //버튼 및 유저 정보 갱신 
        setUpdateButtonVisible();
        updateUserInfoLabel();
    }

    /**
     * 센터 정보를 테이블에 표시합니다.
     */
    private void displayCenters() {
        List<CenterDTO> centers = centerController.getCenterDetails(null);

        DefaultTableModel model = (DefaultTableModel) centerTable.getModel();
        model.setRowCount(0);

        for (CenterDTO center : centers) {
            model.addRow(new Object[]{center.getName(), center.getAddress(), center.getPhone(), center.getBusinessHour(), center.getDescription()});
        }
    }
    
    /**
     * 상세 설명을 보여주는 액션 리스너 클래스
     */
    private class DetailActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = centerTable.getSelectedRow();
            if (selectedRow != -1) {
                String centerName = (String) centerTable.getValueAt(selectedRow, 0);
                String description = (String) centerTable.getValueAt(selectedRow, 4);

                String message = description;

                if (loggedInUser != null && "employee".equals(loggedInUser.getRole())) {
                    List<CenterDTO> allCenters = centerController.getCenterDetails(null);
                    String centerId = null;
                    for (CenterDTO center : allCenters) {
                        if (center.getName().equals(centerName)) {
                            centerId = center.getCenterId();
                            break;
                        }
                    }
                    if (centerId != null) {
                        int classCount = centerController.getClassCount(centerId);
                        int memberCount = centerController.getMemberCount(centerId);
                        message += "\n\n현재 개설된 수업 수: " + classCount + "\n등록중인 회원 수: " + memberCount;
                    }
                }

                JOptionPane.showMessageDialog(CenterUI.this, message, "상세설명", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(CenterUI.this, "상세설명을 볼 센터를 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * 센터 정보를 수정하는 액션 리스너 클래스
     */
    private class UpdateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = centerTable.getSelectedRow();
            if (selectedRow != -1) {
                String centerName = (String) centerTable.getValueAt(selectedRow, 0);
                List<CenterDTO> allCenters = centerController.getCenterDetails(null);
                String centerId = null;
                for (CenterDTO center : allCenters) {
                    if (center.getName().equals(centerName)) {
                        centerId = center.getCenterId();
                        break;
                    }
                }
                if (centerId == null) {
                    JOptionPane.showMessageDialog(CenterUI.this, "해당 센터 정보를 찾을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<CenterDTO> centerDetails = centerController.getCenterDetails(centerId);
                if (centerDetails.isEmpty()) {
                    JOptionPane.showMessageDialog(CenterUI.this, "해당 센터 정보를 찾을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CenterDTO center = centerDetails.get(0);

                JTextField nameField = new JTextField(center.getName());
                JTextField addressField = new JTextField(center.getAddress());
                JTextField phoneField = new JTextField(center.getPhone());
                JTextField businessHourField = new JTextField(center.getBusinessHour());
                JTextField descriptionField = new JTextField(center.getDescription());

                JPanel updatePanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5); // Padding 설정
                gbc.fill = GridBagConstraints.HORIZONTAL;

                gbc.gridx = 0;
                gbc.gridy = 0;
                updatePanel.add(new JLabel("센터명:"), gbc);
                gbc.gridx = 1;
                updatePanel.add(nameField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                updatePanel.add(new JLabel("주소:"), gbc);
                gbc.gridx = 1;
                updatePanel.add(addressField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                updatePanel.add(new JLabel("전화번호:"), gbc);
                gbc.gridx = 1;
                updatePanel.add(phoneField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                updatePanel.add(new JLabel("운영시간:"), gbc);
                gbc.gridx = 1;
                updatePanel.add(businessHourField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                updatePanel.add(new JLabel("시설정보:"), gbc);
                gbc.gridx = 1;
                updatePanel.add(descriptionField, gbc);
                
                int result = JOptionPane.showConfirmDialog(CenterUI.this, updatePanel, "원하는 부분을 수정하고 확인 버튼을 누르세요", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    center.setName(nameField.getText());
                    center.setAddress(addressField.getText());
                    center.setPhone(phoneField.getText());
                    center.setBusinessHour(businessHourField.getText());
                    center.setDescription(descriptionField.getText());

                    centerController.updateCenter(center);
                    displayCenters();
                }
            } else {
                JOptionPane.showMessageDialog(CenterUI.this, "수정할 센터를 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * 로그인/로그아웃 버튼을 업데이트합니다.
     */
    private void updateLoginLogoutButton() {
        if (loggedInUser != null) {
            loginLogoutButton.setText("로그아웃");
            loginLogoutButton.addActionListener(e -> logout());
        } else {
            loginLogoutButton.setText("로그인");
            loginLogoutButton.addActionListener(e -> login());
        }
    }
    
    /**
     * 로그인을 처리하는 메서드입니다.
     */
    private void login() {
        LoginUI loginUI = new LoginUI();
        loginUI.setVisible(true);
        loginUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loginUI.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                UserDTO user = loginUI.getLoggedInUser();
                if (user != null) {
                    loggedInUser = user;
                    refreshUI();
                }
            }
        });
    }
    
    /**
     * 로그아웃을 처리하는 메서드입니다.
     */
    private void logout() {
        loggedInUser = null;        
        refreshUI();
    }

    /**
     * 센터 UI를 갱신하는 메서드입니다.
     */
    private void refreshUI() {
        setUpdateButtonVisible();
        updateLoginLogoutButton();
        updateUserInfoLabel();
        displayCenters();
    }
    
    /**
     * 사용자 정보를 갱신하여 레이블에 표시하는 메서드입니다.
     */
    private void updateUserInfoLabel() {
        if (loggedInUser != null) {
            String role = "member".equals(loggedInUser.getRole()) ? "회원님" : "관리자님";
            userLabel.setText("현재 사용자: " + loggedInUser.getName() + " " + role);
        } else {
            userLabel.setText("현재 사용자: 게스트");
        }
    }
    
    /**
     * 수정 버튼의 활성화 여부를 설정하는 메서드입니다.
     * 로그인 상태 및 사용자 역할에 따라 버튼을 보이거나 숨깁니다.
     */
    private void setUpdateButtonVisible() {
        // 로그인하지 않은 경우 또는 회원인 경우에는 수정 버튼을 숨김
        if (loggedInUser == null || "member".equals(loggedInUser.getRole())) {
            updateButton.setVisible(false);
        }
        else if ("employee".equals(loggedInUser.getRole())) {
            updateButton.setVisible(true);
        }
    }

    /**
     * 프로그램의 메인 메서드입니다.
     * @param args 명령행 인수 배열
     */
    public static void main(String[] args) {
        new CenterUI();
    }
}

    
   
