package com.powerFitness.ui;

import com.powerFitness.controller.InstructorController;
import com.powerFitness.dto.InstructorDTO;
import com.powerFitness.MainFrame;
import com.powerFitness.controller.CenterController;
import com.powerFitness.dto.CenterDTO;
import com.powerFitness.dto.UserDTO;
import com.powerFitness.ui.LoginUI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class InstructorUI extends JFrame {

    private InstructorController instructorController;
    private CenterController centerController;
    private JTable instructorTable;
    private JButton addInstructorButton;
    private JButton updateInstructorButton;
    private JButton deleteInstructorButton;
    private JButton returnToMainButton;
    private JButton loginLogoutButton;
    private JLabel userLabel;

    private UserDTO loggedInUser;
    
    private MainFrame mainFrame;
    
    /**
     * 생성자 함수 
     */
    public InstructorUI() {
        instructorController = new InstructorController();
        centerController = new CenterController();
        initUI();
        displayInstructors(null);
    }
    
    public InstructorUI(UserDTO loggedInUser) {
    	this.loggedInUser = loggedInUser;
        instructorController = new InstructorController();
        //센터 컨트롤러 추가 
        centerController = new CenterController(); 
        initUI();
        displayInstructors(null);
    }
    
    /**
     * 메인페이지에서 넘어올 경우 생성자함수 추가 
     * @param loggedInUser
     * @param mainFrame
     */
    public InstructorUI(UserDTO loggedInUser, MainFrame mainFrame ) {
    	this.loggedInUser = loggedInUser;
    	this.mainFrame = mainFrame;
        instructorController = new InstructorController();
        //센터 컨트롤러 추가 
        centerController = new CenterController(); 
        initUI();
        displayInstructors(null);
    }
    
    /**
     * 버튼 활성화 메서드 
     */
    private void setButtonVisible() {
        // 로그인하지 않은 경우 또는 회원인 경우에는 수정 버튼을 숨김
        if (loggedInUser == null || "member".equals(loggedInUser.getRole())) {
        	addInstructorButton.setVisible(false);
            updateInstructorButton.setVisible(false);
            deleteInstructorButton.setVisible(false);
        }
        else if ("employee".equals(loggedInUser.getRole())) {
        	addInstructorButton.setVisible(true);
        	updateInstructorButton.setVisible(true);
        	deleteInstructorButton.setVisible(true);
        }
    }
    
    /**
     * 초기화면 구성 메서드 
     */
    private void initUI() {
        setTitle("Power Fitness - 강사 목록");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String userInfo = loggedInUser != null ? "현재 사용자: " + loggedInUser.getName() + " (" + loggedInUser.getRole() + ")" : "현재 사용자: 게스트";
        userLabel = new JLabel(userInfo);
        userPanel.add(userLabel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addInstructorButton = new JButton("강사 등록하기");
        addInstructorButton.addActionListener(new AddInstructorActionListener());
        buttonPanel.add(addInstructorButton);

        updateInstructorButton = new JButton("강사 수정하기");
        updateInstructorButton.addActionListener(new UpdateInstructorActionListener());
        buttonPanel.add(updateInstructorButton);

        deleteInstructorButton = new JButton("강사 삭제하기");
        deleteInstructorButton.addActionListener(new DeleteInstructorActionListener());
        buttonPanel.add(deleteInstructorButton);
        
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
       

        // userPanel과 buttonPanel을 포함하는 패널 생성
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(userPanel, BorderLayout.NORTH);
        northPanel.add(buttonPanel, BorderLayout.CENTER);

        instructorTable = new JTable();
        instructorTable.setModel(new DefaultTableModel(new Object[]{"강사ID", "소속센터", "이름", "생일", "전화번호", "성별", "입사일", "퇴사일", "주소"}, 0));
        JScrollPane scrollPane = new JScrollPane(instructorTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        //버튼 및 유저 정보 갱신 
        setButtonVisible();
        updateUserInfoLabel();
    }

    /**
     * 강사 조회 메서드 
     * @param instructorId
     */
    private void displayInstructors(String instructorId) {
        List<InstructorDTO> instructors = instructorController.getInstructorDetails(instructorId);

        DefaultTableModel model = (DefaultTableModel) instructorTable.getModel();
        model.setRowCount(0);

        // 열 제목 설정
        if (loggedInUser == null || "member".equals(loggedInUser.getRole())) {
            model.setColumnIdentifiers(new Object[]{"소속센터", "이름", "전화번호", "성별"});
        } else {
            model.setColumnIdentifiers(new Object[]{"강사ID", "소속센터", "이름", "생일", "전화번호", "성별", "입사일", "퇴사일", "주소"});
        }

        for (InstructorDTO instructor : instructors) {
            // 센터 이름 가져오기
            String centerName = getCenterNameById(instructor.getCenterId());
            
            if (loggedInUser == null || "member".equals(loggedInUser.getRole())) {
                model.addRow(new Object[]{centerName, instructor.getName(),
                        instructor.getPhone(), instructor.getGender()});
            } else {
                model.addRow(new Object[]{instructor.getInstructorId(), centerName, instructor.getName(),
                        instructor.getBirthday(), instructor.getPhone(), instructor.getGender(), instructor.getJoinDate(),
                        instructor.getQuitDate(), instructor.getAddress()});
            }
        }
    }

    /**
     * 센터 ID -> 이름 변경 메서드 
     * @param centerId
     * @return
     */
    private String getCenterNameById(String centerId) {
        List<CenterDTO> centers = centerController.getCenterDetails(null);
        for (CenterDTO center : centers) {
            if (center.getCenterId().equals(centerId)) {
                return center.getName();
            }
        }
        return "Unknown";
    }
    
    /**
     * 강사 등록 메서드 
     * @author jinnie
     *
     */
    private class AddInstructorActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<CenterDTO> centers = centerController.getCenterDetails(null);
            JComboBox<String> centerComboBox = new JComboBox<>();
            for (CenterDTO center : centers) {
                centerComboBox.addItem(center.getName());
            }

            JTextField nameField = new JTextField();
            JTextField birthdayField = new JTextField();
            JTextField phoneField = new JTextField();
            JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"F", "M"});
            JTextField joinDateField = new JTextField();
            JTextField quitDateField = new JTextField();
            JTextField addressField = new JTextField();

            JPanel addPanel = new JPanel(new GridLayout(9, 2));
            addPanel.add(new JLabel("소속센터:"));
            addPanel.add(centerComboBox);
            addPanel.add(new JLabel("이름:"));
            addPanel.add(nameField);
            addPanel.add(new JLabel("생일:"));
            addPanel.add(birthdayField);
            addPanel.add(new JLabel("전화번호:"));
            addPanel.add(phoneField);
            addPanel.add(new JLabel("성별:"));
            addPanel.add(genderComboBox);
            addPanel.add(new JLabel("입사일:"));
            addPanel.add(joinDateField);
            addPanel.add(new JLabel("퇴사일:"));
            addPanel.add(quitDateField);
            addPanel.add(new JLabel("주소:"));
            addPanel.add(addressField);

            int result = JOptionPane.showConfirmDialog(InstructorUI.this, addPanel, "강사 등록", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String selectedCenterName = (String) centerComboBox.getSelectedItem();
                String centerId = null;
                for (CenterDTO center : centers) {
                    if (center.getName().equals(selectedCenterName)) {
                        centerId = center.getCenterId();
                        break;
                    }
                }

                // Automatically generate instructor ID
                String instructorId = generateInstructorId(centerId);

                // Parse the text fields and create a new InstructorDTO object
                InstructorDTO instructorDTO = new InstructorDTO(
                        instructorId,
                        centerId,
                        nameField.getText(),
                        java.sql.Date.valueOf(birthdayField.getText()),
                        phoneField.getText(),
                        genderComboBox.getSelectedItem().toString().charAt(0),
                        java.sql.Date.valueOf(joinDateField.getText()),
                        !quitDateField.getText().isEmpty() ? java.sql.Date.valueOf(quitDateField.getText()) : null,
                        addressField.getText()
                );

                // Pass the InstructorDTO object to the controller to add to the database
                instructorController.addInstructor(instructorDTO);

                // Refresh the table to reflect the changes
                displayInstructors(null);
            }
        }

        /**
         *  강사 아이디 자동 생성 메서드
         * @param centerId
         * @return
         */
        private String generateInstructorId(String centerId) {
            // 강사 아이디 생성을 InstructorDAO에서 처리하므로 해당 메서드 호출
            String lastInstructorId = instructorController.getLastInstructorId(centerId);
            int newIdNumber = Integer.parseInt(lastInstructorId.substring(11)) + 1;

            return "instructor" + String.format("%05d", newIdNumber);
        }
    }
    
    /**
     * 센터 이름->ID 변환 메서드 
     * @param centerName
     * @return
     */
    private String getCenterIdByName(String centerName) {
        List<CenterDTO> centers = centerController.getCenterDetails(null);
        for (CenterDTO center : centers) {
            if (center.getName().equals(centerName)) {
                return center.getCenterId();
            }
        }
        return null;
    }
    
    /**
     * 강사 수정 메서드 
     * @author jinnie
     *
     */
    private class UpdateInstructorActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = instructorTable.getSelectedRow();
            if (selectedRow != -1) {
                // Get the selected instructor's details from the table
              String instructorId = instructorTable.getValueAt(selectedRow, 0).toString();
                String centerName = instructorTable.getValueAt(selectedRow, 1).toString(); // 센터 이름 가져오기
                String centerId = getCenterIdByName(centerName);
                String name = instructorTable.getValueAt(selectedRow, 2).toString();
                String birthday = instructorTable.getValueAt(selectedRow, 3).toString();
                String phone = instructorTable.getValueAt(selectedRow, 4).toString();
                char gender = instructorTable.getValueAt(selectedRow, 5).toString().charAt(0);
                String joinDate = instructorTable.getValueAt(selectedRow, 6).toString();
                String quitDate = instructorTable.getValueAt(selectedRow, 7) != null ? instructorTable.getValueAt(selectedRow, 7).toString() : null;
                String address = instructorTable.getValueAt(selectedRow, 8).toString();
                
                // Create text fields and populate them with the selected instructor's details

                JTextField phoneField = new JTextField(phone);
                JTextField quitDateField = new JTextField(quitDate != null ? quitDate : "");
                JTextField addressField = new JTextField(address);
                JLabel nameLabel = new JLabel(name);
                JLabel birthdayLabel = new JLabel(birthday);
                JLabel genderLabel = new JLabel(String.valueOf(gender));
                JLabel centerLabel = new JLabel(centerName);
                JLabel joinDateLabel = new JLabel(joinDate);
                
                // Create a panel with text fields for editing instructor details
                JPanel updatePanel = new JPanel(new GridLayout(9, 2));
                updatePanel.add(new JLabel("이름:"));
                updatePanel.add(nameLabel);
                updatePanel.add(new JLabel("생일:"));
                updatePanel.add(birthdayLabel);
                updatePanel.add(new JLabel("전화번호:"));
                updatePanel.add(phoneField);
                updatePanel.add(new JLabel("성별:"));
                updatePanel.add(genderLabel);
                updatePanel.add(new JLabel("소속 센터:"));
                updatePanel.add(centerLabel);
                updatePanel.add(new JLabel("입사일:"));
                updatePanel.add(joinDateLabel);
                updatePanel.add(new JLabel("퇴사일:"));
                updatePanel.add(quitDateField);
                updatePanel.add(new JLabel("주소:"));
                updatePanel.add(addressField);

                int result = JOptionPane.showConfirmDialog(InstructorUI.this, updatePanel, "강사 수정", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                	
                    InstructorDTO updatedInstructor = new InstructorDTO(                
                    		instructorId,                   
                    		centerId,
                    		name,
                            java.sql.Date.valueOf(birthday),                    
                            phoneField.getText(),
                            gender,
                            java.sql.Date.valueOf(joinDate),
                            !quitDateField.getText().isEmpty() ? java.sql.Date.valueOf(quitDateField.getText()) : null,
                            addressField.getText()
                    );

                    
                    instructorController.updateInstructor(updatedInstructor);

                    // 새로고침 
                    displayInstructors(null);
                }
            } else {
                JOptionPane.showMessageDialog(InstructorUI.this, "수정할 강사를 선택해주세요.", "강사 선택", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * 강사 삭제 메서드 
     * @author jinnie
     *
     */
    private class DeleteInstructorActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = instructorTable.getSelectedRow();
            if (selectedRow != -1) {

            	//ID 안숨겼을때 버전 
            	String instructorId = instructorTable.getValueAt(selectedRow, 0).toString();
            	//ID 숨겼을때 버전 
//            	String instructorId = (String) ((DefaultTableModel) instructorTable.getModel()).getValueAt(selectedRow, 0);
                // Confirm deletion with the user
                int option = JOptionPane.showConfirmDialog(InstructorUI.this,
                        "선택한 강사를 삭제하시겠습니까?", "강사 삭제", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    instructorController.deleteInstructor(instructorId);
                    
                    //새로고침 
                    displayInstructors(null);
                }
            } else {
                JOptionPane.showMessageDialog(InstructorUI.this, "삭제할 강사를 선택해주세요.", "강사 선택", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    /**
     * 로그인 버튼 갱신 메서드 
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
     *  로그인을 처리하는 메서드
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
     * 로그아웃 메서드 
     */
    private void logout() {
        loggedInUser = null;        
        refreshUI();
    }

    /**
     *  강사 UI를 갱신하는 메서드
     */
    private void refreshUI() {
        setButtonVisible();
        updateLoginLogoutButton();
        updateUserInfoLabel();
        displayInstructors(null);
    }
    /**
     * 유저 정보 갱신 메서드 
     */
    private void updateUserInfoLabel() {
        if (loggedInUser != null) {
            String role = "member".equals(loggedInUser.getRole()) ? "회원님" : "관리자님";
            userLabel.setText("현재 사용자: " + loggedInUser.getName() + " " + role);
        } else {
            userLabel.setText("현재 사용자: 게스트");
        }
    }
    

    public static void main(String[] args) {
        new InstructorUI();
    }
}
