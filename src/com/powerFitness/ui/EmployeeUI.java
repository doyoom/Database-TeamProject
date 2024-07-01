package com.powerFitness.ui;

import com.powerFitness.controller.EmployeeController;
import com.powerFitness.controller.InstructorController;
import com.powerFitness.MainFrame;
import com.powerFitness.controller.CenterController;
import com.powerFitness.dto.EmployeeDTO;
import com.powerFitness.dto.UserDTO;
import com.powerFitness.dto.CenterDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

public class EmployeeUI extends JFrame {

    private EmployeeController employeeController;
    private CenterController centerController;
    private JTable employeeTable;
    private JButton addEmployeeButton;
    private JButton updateEmployeeButton;
    private JButton deleteEmployeeButton;
    private JButton loginLogoutButton;
    private JLabel userLabel;
    
    private UserDTO loggedInUser;
    
    //메인페이지 관련 변수
    private MainFrame mainFrame;
    private JButton returnToMainButton;
    
    /*
     * 기본 생성자 함수 
     */
    public EmployeeUI() {
        employeeController = new EmployeeController();
        centerController = new CenterController();
        initUI();
        displayEmployees(null);
    }
    
    public EmployeeUI(UserDTO loggedInUser) {
    	this.loggedInUser = loggedInUser;
        employeeController = new EmployeeController();
        //센터 컨트롤러 추가 
        centerController = new CenterController(); 
        initUI();
        displayEmployees(null);
    }
    
    /*
     * 메인페이지에서 넘어올 경우 생성자함수 추가 
     */
    public EmployeeUI(UserDTO loggedInUser, MainFrame mainFrame ) {
    	this.loggedInUser = loggedInUser;
    	this.mainFrame = mainFrame;
    	employeeController = new EmployeeController();
        //센터 컨트롤러 추가 
        centerController = new CenterController(); 
        initUI();
        displayEmployees(null);
    }
    
    /*
     * 초기화면 메서드
     */
    private void initUI() {
        setTitle("Power Fitness - 직원 목록");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //현재 사용자 나타냄 
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String userInfo = loggedInUser != null ? "현재 사용자: " + loggedInUser.getName() + " (" + loggedInUser.getRole() + ")" : "현재 사용자: 게스트";
        userLabel = new JLabel(userInfo);
        String role = "member".equals(loggedInUser.getRole()) ? "회원님" : "관리자님";
        userLabel.setText("현재 사용자: " + loggedInUser.getName() + " " + role);
        userPanel.add(userLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addEmployeeButton = new JButton("직원 등록하기");
        addEmployeeButton.addActionListener(new AddEmployeeActionListener());
        buttonPanel.add(addEmployeeButton);

        updateEmployeeButton = new JButton("직원 정보 수정");
        updateEmployeeButton.addActionListener(new UpdateEmployeeActionListener());
        buttonPanel.add(updateEmployeeButton);

        deleteEmployeeButton = new JButton("직원 삭제");
        deleteEmployeeButton.addActionListener(new DeleteEmployeeActionListener());
        buttonPanel.add(deleteEmployeeButton);
        
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

        employeeTable = new JTable();
        employeeTable.setModel(new DefaultTableModel(new Object[]{"직원ID", "소속센터", "이름", "생일", "전화번호", "성별", "입사일", "퇴사일", "주소", "직급", "비밀번호"}, 0));
      
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        getContentPane().setLayout(new BorderLayout());
     
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /*
     * 직원 조회 메서드 
     */
    private void displayEmployees(String employeeId) {
        List<EmployeeDTO> employees = employeeController.getEmployeeDetails(employeeId);

        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.setRowCount(0);

        for (EmployeeDTO employee : employees) {
        	String centerName = getCenterNameById(employee.getCenterId());
            model.addRow(new Object[]{employee.getEmployeeId(), centerName, employee.getName(),
                    employee.getBirthday(), employee.getPhone(), employee.getGender(), employee.getJoinDate(),
                    employee.getQuitDate(), employee.getAddress(), employee.getTitle(), employee.getPassword()});
        }
    }
    
    /*
     * 센터 id->이름 변환 메서드 
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

    /*
     * 직원 등록 메서드 
     */
    private class AddEmployeeActionListener implements ActionListener {
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
            JTextField titleField = new JTextField();
            JTextField passwordField = new JTextField();
            

            JPanel addPanel = new JPanel(new GridLayout(11, 2));
            addPanel.add(new JLabel("소속센터:"));
            addPanel.add(centerComboBox);
            addPanel.add(new JLabel("이름:"));
            addPanel.add(nameField);
            addPanel.add(new JLabel("비밀번호:"));
            addPanel.add(passwordField);
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
            addPanel.add(new JLabel("직급:"));
            addPanel.add(titleField);
            
            int result = JOptionPane.showConfirmDialog(EmployeeUI.this, addPanel, "직원 등록", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String selectedCenterName = (String) centerComboBox.getSelectedItem();
                String centerId = null;
                for (CenterDTO center : centers) {
                    if (center.getName().equals(selectedCenterName)) {
                        centerId = center.getCenterId();
                        break;
                    }
                }

                // 직원 ID 자동 생성 
                String employeeId = generateEmployeeId(centerId);

                EmployeeDTO employeeDTO = new EmployeeDTO(
                        employeeId,
                        centerId,
                        nameField.getText(),
                        java.sql.Date.valueOf(birthdayField.getText()),
                        phoneField.getText(),
                        genderComboBox.getSelectedItem().toString().charAt(0),
                        java.sql.Date.valueOf(joinDateField.getText()),
                        !quitDateField.getText().isEmpty() ? java.sql.Date.valueOf(quitDateField.getText()) : null,
                        addressField.getText(),
                        passwordField.getText(),
                        titleField.getText()
                );

                // 직원 등록 
                employeeController.addEmployee(employeeDTO);

                // 조회페이지 새로고침 
                displayEmployees(null);
            }
        }
        
    /*
     *  직원 아이디 생성 메서드
     */
    private String generateEmployeeId(String centerId) {
        // 강사 아이디 생성을 InstructorDAO에서 처리하므로 해당 메서드 호출
        String lastEmployeeId = employeeController.getLastEmployeeId(centerId);
        int newIdNumber = Integer.parseInt(lastEmployeeId.substring(9))+1;
 
        return "employee"+String.format("%05d", newIdNumber);
        }
    }

    //직원 수정 메서드 
    private class UpdateEmployeeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow != -1) {
             
            	String employeeId = employeeTable.getValueAt(selectedRow, 0).toString();
                String centerName = employeeTable.getValueAt(selectedRow, 1).toString(); // 센터 이름 가져오기
                String centerId = getCenterIdByName(centerName);
                String name = employeeTable.getValueAt(selectedRow, 2).toString();
                String birthday = employeeTable.getValueAt(selectedRow, 3).toString();
                String phone = employeeTable.getValueAt(selectedRow, 4).toString();
                char gender = employeeTable.getValueAt(selectedRow, 5).toString().charAt(0);
                String joinDate = employeeTable.getValueAt(selectedRow, 6).toString();
                String quitDate = employeeTable.getValueAt(selectedRow, 7) != null ? employeeTable.getValueAt(selectedRow, 7).toString() : null;
                String address = employeeTable.getValueAt(selectedRow, 8).toString();
                String title = employeeTable.getValueAt(selectedRow, 9).toString();
                String password = employeeTable.getValueAt(selectedRow, 10).toString();
                
                JTextField phoneField = new JTextField(phone);
                JTextField quitDateField = new JTextField(quitDate != null ? quitDate : "");
                JTextField addressField = new JTextField(address);
                JTextField titleField = new JTextField(title);
                JTextField passwordField = new JTextField(password);
                JLabel nameLabel = new JLabel(name);
                JLabel birthdayLabel = new JLabel(birthday);
                JLabel genderLabel = new JLabel(String.valueOf(gender));
                JLabel centerLabel = new JLabel(centerName);
                JLabel joinDateLabel = new JLabel(joinDate);
               
                JPanel updatePanel = new JPanel(new GridLayout(11, 2));
                updatePanel.add(new JLabel("이름:"));
                updatePanel.add(nameLabel);
                updatePanel.add(new JLabel("비밀번호:"));
                updatePanel.add(passwordField);
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
                updatePanel.add(new JLabel("직급:"));
                updatePanel.add(titleField);

                int result = JOptionPane.showConfirmDialog(EmployeeUI.this, updatePanel, "강사 수정", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                	
                    
                    EmployeeDTO updatedEmployee = new EmployeeDTO(                
                    		employeeId,                   
                    		centerId,
                    		name,
                            java.sql.Date.valueOf(birthday),                    
                            phoneField.getText(),
                            gender,
                            java.sql.Date.valueOf(joinDate),
                            !quitDateField.getText().isEmpty() ? java.sql.Date.valueOf(quitDateField.getText()) : null,
                            addressField.getText(),
                            passwordField.getText(),
                            titleField.getText()
                    );

                     employeeController.updateEmployee(updatedEmployee);
                     displayEmployees(null);
                }
            } else {
                JOptionPane.showMessageDialog(EmployeeUI.this, "수정할 직원을 선택해주세요.", "직원 선택", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    /*
     * 센터 이름 -> ID 변환 메서드 
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
    /*
     * 직원 삭제 메서드 
     */
    private class DeleteEmployeeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow != -1) {
                String employeeId = employeeTable.getValueAt(selectedRow, 0).toString();
             
                int option = JOptionPane.showConfirmDialog(EmployeeUI.this,
                        "선택한 직원을 삭제하시겠습니까?", "직원 삭제", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                  
                    employeeController.deleteEmployee(employeeId);
                    displayEmployees(null);
                }
            } else {
                JOptionPane.showMessageDialog(EmployeeUI.this, "삭제할 직원을 선택해주세요.", "직원 선택", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    /*
     * 로그인 버튼 로그아웃으로 고정 
     */
    private void updateLoginLogoutButton() {
            loginLogoutButton.setText("로그아웃");
            loginLogoutButton.addActionListener(e -> logout());    
    }
    
    /*
     * 로그아웃 메서드 
     */
    private void logout() {
        loggedInUser = null;
        getContentPane().removeAll();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        dispose();
    }


    public static void main(String[] args) {
        new EmployeeUI();
    }
}