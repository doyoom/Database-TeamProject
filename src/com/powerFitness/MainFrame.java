package com.powerFitness;

import java.awt.*;
import javax.swing.*;
import com.powerFitness.dto.UserDTO;
import com.powerFitness.ui.*;
import com.powerFitness.ui.MemberUI.*; // 추가 

public class MainFrame extends JFrame {
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JButton classViewButton;
    private JButton instructorViewButton;
    private JButton centerViewButton;
    private JButton signupButton;
    private JButton loginButton;
    private UserDTO loggedInUser;
    private JLabel userInfoLabel;
    
    /**
     * 초기 메인화면 페이지  
     */
    public MainFrame() {
        setTitle("Power Fitness");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel = new JLabel("파워피트니스");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitleLabel = new JLabel("파워피트니스 소개");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        classViewButton = new JButton("수업 조회");
        classViewButton.setMaximumSize(new Dimension(200, 50));
        classViewButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        instructorViewButton = new JButton("강사 조회");
        instructorViewButton.setMaximumSize(new Dimension(200, 50));
        instructorViewButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerViewButton = new JButton("센터 조회");
        centerViewButton.setMaximumSize(new Dimension(200, 50));
        centerViewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //수업조회 버튼 클릭시 동작 
        classViewButton.addActionListener(e -> {
            ClassUI classUI = new ClassUI(loggedInUser, this);
            classUI.setVisible(true);
            dispose();
        });
        
        //강사조회 버튼 클릭시 동작 
        instructorViewButton.addActionListener(e -> {
        	InstructorUI instructorUI = new InstructorUI(loggedInUser, this);
        	instructorUI.setVisible(true);
        	dispose();
        });
        
        //센터조회 버튼 클릭시 동작 
        centerViewButton.addActionListener(e -> {
        	CenterUI centerUI = new CenterUI(loggedInUser,this);
        	centerUI.setVisible(true);
        	dispose();
        });
        
        

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(titleLabel);
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(classViewButton);
        centerPanel.add(instructorViewButton);
        centerPanel.add(centerViewButton);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupButton = new JButton("회원가입");
        loginButton = new JButton("로그인");
        bottomPanel.add(signupButton);
        bottomPanel.add(loginButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> showLogin());
        signupButton.addActionListener(e -> showSignUp());
    }
    

    /**
     * 회원 로그인 시 메인 화면 페이지 
     */
    public void showMemberMainFrame(UserDTO user) {
        this.loggedInUser = user;

        getContentPane().removeAll();
        setTitle("Member Main Frame - Power Fitness");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel = new JLabel("파워피트니스");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userInfoLabel = new JLabel();
        userInfoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        userInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        classViewButton = new JButton("수업 조회");
        classViewButton.setMaximumSize(new Dimension(200, 50));
        classViewButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        instructorViewButton = new JButton("강사 조회");
        instructorViewButton.setMaximumSize(new Dimension(200, 50));
        instructorViewButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerViewButton = new JButton("센터 조회");
        centerViewButton.setMaximumSize(new Dimension(200, 50));
        centerViewButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(userInfoLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(classViewButton);
        centerPanel.add(instructorViewButton);
        centerPanel.add(centerViewButton);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton logoutButton = new JButton("로그아웃");
        JButton myPageButton = new JButton("마이페이지");

        bottomPanel.add(logoutButton);
        bottomPanel.add(myPageButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        //수업조회 버튼 클릭시 동작 
        classViewButton.addActionListener(e -> {
            ClassUI classUI = new ClassUI(loggedInUser, this);
            classUI.setVisible(true);
            dispose();
        });
        
        //강사조회 버튼 클릭시 동작 
        instructorViewButton.addActionListener(e -> {
        	InstructorUI instructorUI = new InstructorUI(loggedInUser, this);
        	instructorUI.setVisible(true);
        	dispose();
        });
        
        //센터조회 버튼 클릭시 동작 
        centerViewButton.addActionListener(e -> {
        	CenterUI centerUI = new CenterUI(loggedInUser,this);
        	centerUI.setVisible(true);
        	dispose();
        });
        
        // 버튼 동작
        logoutButton.addActionListener(e -> logout());
        myPageButton.addActionListener(e -> openMyPage());

        updateUserInfoLabel();
        revalidate();
        repaint();
    }

    /**
     * 관리자(직원) 로그인 시 메인 화면 페이지 
     */
    public void showAdminMainFrame(UserDTO user) {
        this.loggedInUser = user;

        getContentPane().removeAll();
        setTitle("Admin Main Frame - Power Fitness");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel = new JLabel("파워피트니스 관리 페이지");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userInfoLabel = new JLabel();
        userInfoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        userInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton classManageButton = new JButton("수업 관리");
        classManageButton.setMaximumSize(new Dimension(200, 50));
        classManageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton classAttendButton = new JButton("수강 관리");
        classAttendButton.setMaximumSize(new Dimension(200, 50));
        classAttendButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton paymentManageButton = new JButton("결제 관리");
        paymentManageButton.setMaximumSize(new Dimension(200, 50));
        paymentManageButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton memberManageButton = new JButton("회원 관리");
        memberManageButton.setMaximumSize(new Dimension(200, 50));
        memberManageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton instructorManageButton = new JButton("강사 관리");
        instructorManageButton.setMaximumSize(new Dimension(200, 50));
        instructorManageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton employeeManageButton = new JButton("직원 관리");
        employeeManageButton.setMaximumSize(new Dimension(200, 50));
        employeeManageButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton centerManageButton = new JButton("센터 관리");
        centerManageButton.setMaximumSize(new Dimension(200, 50));
        centerManageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 수업 관리 버튼 클릭시 동작 
        classManageButton.addActionListener(e -> {
            ClassUI classUI = new ClassUI(loggedInUser, this);
            classUI.setVisible(true);
            classUI.showManageView();
            dispose();
        });
        
        // 수강 관리 버튼 클릭시 동작 
        classAttendButton.addActionListener(e -> {
            AdminAttendingUI adminAttendingUI = new AdminAttendingUI(this, loggedInUser, true); 
            adminAttendingUI.setVisible(true);
        });
        
        // 결제 관리 버튼 클릭시 동작 
        paymentManageButton.addActionListener(e -> {
            AdminPaymentUI adminPaymentUI = new AdminPaymentUI(this, loggedInUser, true);
            adminPaymentUI.setVisible(true);
        });
        
        memberManageButton.addActionListener(e -> {
            MemberListUI memberListUI = new MemberListUI(this, true);  // `frame`은 현재 JFrame 인스턴스를 의미
            memberListUI.setVisible(true);
        });


        
        // 강사 관리 버튼 클릭시 동작 
        instructorManageButton.addActionListener(e -> {
        	InstructorUI instructorUI = new InstructorUI(loggedInUser, this);
        	instructorUI.setVisible(true);
        	dispose();
        });
        
        // 직원 관리 버튼 클릭시 동작 
        employeeManageButton.addActionListener(e -> {
        	EmployeeUI employeeUI = new EmployeeUI(loggedInUser, this);
        	employeeUI.setVisible(true);
        	dispose();
        });
        
        // 센터 관리 버튼 클릭시 동작
        centerManageButton.addActionListener(e -> {
        	CenterUI centerUI = new CenterUI(loggedInUser,this);
        	centerUI.setVisible(true);
        	dispose();
        });

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(userInfoLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(classManageButton);
        centerPanel.add(classAttendButton);
        centerPanel.add(paymentManageButton);
        centerPanel.add(memberManageButton);
        centerPanel.add(instructorManageButton);
        centerPanel.add(employeeManageButton);
        centerPanel.add(centerManageButton);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton logoutButton = new JButton("로그아웃");
        JButton myPageButton = new JButton("마이페이지");

        bottomPanel.add(logoutButton);
        bottomPanel.add(myPageButton);
        add(bottomPanel, BorderLayout.SOUTH);


        // 버튼 동작
        logoutButton.addActionListener(e -> logout());
        myPageButton.addActionListener(e -> openMyPage());

        updateUserInfoLabel();
        revalidate();
        repaint();
    }

    private void openMyPage() {
        // 사용자의 memberId를 사용하여 마이페이지 열기
        MemberProfileUI profileUI = new MemberProfileUI(this, loggedInUser.getUserId(), true); // `this`는 MainFrame 자신을 가리킵니다.
        profileUI.setVisible(true);
    }

    /**
     * 로그아웃 메서드 
     */
    private void logout() {
        loggedInUser = null;
        getContentPane().removeAll();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        dispose();
    }

    /**
     * 로그인 메서드 
     */
    private void showLogin() {
    	LoginUI loginUI = new LoginUI();
        loginUI.setVisible(true);
        loginUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loginUI.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                UserDTO user = loginUI.getLoggedInUser();
                if (user != null) {
                    loggedInUser = user;
                    if ("member".equals(loggedInUser.getRole())) {
                    	showMemberMainFrame(loggedInUser);
                    }
                    else {showAdminMainFrame(loggedInUser);
                }
            }}
        });
    }
    
    /**
     * 회원가입 메서드 
     */
    private void showSignUp() {
        MemberSignupUI signupDialog = new MemberSignupUI(this);
        signupDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }

    public void setLoggedInUser(UserDTO loggedInUser) {
        this.loggedInUser = loggedInUser;
        updateUserInfoLabel();
    }

    /**
     * 유저 정보 갱신 메서드 
     */
    private void updateUserInfoLabel() {
        if (loggedInUser != null) {
            String role = "member".equals(loggedInUser.getRole()) ? "회원님" : "관리자님";
            userInfoLabel.setText("안녕하세요, " + loggedInUser.getName() + " " + role + "!");
        } else {
            userInfoLabel.setText("");
        }
    }

    /**
     * 메인화면 이동 메서드 
     */
    public void returnToMain() {
        if (loggedInUser != null) {
            if ("member".equals(loggedInUser.getRole())) {
                showMemberMainFrame(loggedInUser);
            } else if ("employee".equals(loggedInUser.getRole())) {
                showAdminMainFrame(loggedInUser);
            }
        } else {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            dispose();
        }
    }
}
