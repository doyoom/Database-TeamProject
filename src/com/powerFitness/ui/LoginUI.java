package com.powerFitness.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.powerFitness.controller.LoginController;
import com.powerFitness.dto.UserDTO;

/**
 * LoginUI 클래스는 사용자가 로그인할 수 있는 사용자 인터페이스를 제공합니다.
 */
public class LoginUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField phoneField;
    private JPasswordField passwordField;
    private JCheckBox memberCheckBox;
    private JCheckBox employeeCheckBox;
    private JButton loginButton;
    private JLabel messageLabel;
    private LoginController loginController;
    private UserDTO loggedInUser;

    /**
     * LoginUI 생성자.
     */
    public LoginUI() {
        loginController = new LoginController();

        setTitle("로그인");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 설정 변경
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel phoneLabel = new JLabel("전화번호:");
        phoneLabel.setBounds(10, 10, 80, 25);
        add(phoneLabel);

        phoneField = new JTextField(20);
        phoneField.setBounds(100, 10, 160, 25);
        add(phoneField);

        JLabel passwordLabel = new JLabel("비밀번호:");
        passwordLabel.setBounds(10, 40, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 40, 160, 25);
        add(passwordField);

        memberCheckBox = new JCheckBox("회원");
        memberCheckBox.setBounds(10, 70, 80, 25);
        add(memberCheckBox);

        employeeCheckBox = new JCheckBox("관리자");
        employeeCheckBox.setBounds(100, 70, 80, 25);
        add(employeeCheckBox);

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(memberCheckBox);
        roleGroup.add(employeeCheckBox);

        loginButton = new JButton("로그인");
        loginButton.setBounds(10, 100, 250, 25);
        add(loginButton);

        messageLabel = new JLabel("");
        messageLabel.setBounds(10, 130, 250, 25);
        add(messageLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phone = phoneField.getText();
                String password = new String(passwordField.getPassword());
                String role = memberCheckBox.isSelected() ? "member" : employeeCheckBox.isSelected() ? "employee" : null;

                if (role == null) {
                    messageLabel.setText("회원 유형을 선택하세요.");
                    return;
                }

                UserDTO user = loginController.login(phone, password, role);

                if (user != null) {
                    messageLabel.setText("로그인 성공!");
                    loggedInUser = user;
                    dispose();

                    if ("member".equals(role)) {
                        System.out.println("Member logged in: " + user.getUserId());
                    } else if ("employee".equals(role)) {
                        System.out.println("Employee logged in: " + user.getUserId());
                    }
                } else {
                    messageLabel.setText("로그인 실패. 아이디와 비밀번호를 확인하세요.");
                    System.out.println("Login failed for phone: " + phone);
                }
            }
        });
    }

    /**
     * 로그인된 사용자를 반환합니다.
     *
     * @return UserDTO 객체로 로그인된 사용자 정보
     */
    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
}
