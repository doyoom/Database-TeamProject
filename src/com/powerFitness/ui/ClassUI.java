package com.powerFitness.ui;

import com.powerFitness.MainFrame;
import com.powerFitness.controller.ClassController;
import com.powerFitness.controller.EnrollmentController; // 추가 
import com.powerFitness.dto.ClassDTO;
import com.powerFitness.dto.UserDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Power Fitness 수업 관리 UI를 제공하는 클래스입니다.
 */
public class ClassUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JPanel classListView;
    private JPanel classSearchView;
    private JPanel classDetailsView;
    private JPanel classManageView;
    private UserDTO loggedInUser;

    private ClassController classController;
    private EnrollmentController enrollmentController; // 수강신청을 위해 추가 

    private JButton listViewButton;
    private JButton searchViewButton;
    private JButton manageViewButton;
    private JButton loginLogoutButton;
    private JButton returnToMainButton;

    private JLabel userInfoLabel;
    private MainFrame mainFrame;

    /**
     * 로그인한 사용자와 메인프레임을 매개변수로 받아 ClassUI를 초기화합니다.
     * @param loggedInUser 로그인한 사용자
     * @param mainFrame 메인프레임
     */
    public ClassUI(UserDTO loggedInUser, MainFrame mainFrame) {
        this.loggedInUser = loggedInUser;
        this.mainFrame = mainFrame;
        this.classController = new ClassController();
        
        // 수업 페이지
        setTitle("Power Fitness");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        classListView = createClassListView();
        classSearchView = createClassSearchView();
        classDetailsView = new JPanel();
        classManageView = new ClassManageUI(mainFrame, this);

        // 화면 상단 버튼
        mainPanel.add(classListView, "ClassListView");
        mainPanel.add(classSearchView, "ClassSearchView");
        mainPanel.add(classDetailsView, "ClassDetailsView");
        mainPanel.add(classManageView, "ClassManageView");

        JPanel navPanel = new JPanel(new BorderLayout());
        JPanel leftNavPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightNavPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // 사용자 표시
        userInfoLabel = new JLabel();
        leftNavPanel.add(userInfoLabel);

        // 로그인/로그아웃 버튼
        loginLogoutButton = new JButton();
        updateLoginLogoutButton();
        leftNavPanel.add(loginLogoutButton);

        // '메인 화면' 버튼
        returnToMainButton = new JButton("메인 화면");
        returnToMainButton.addActionListener(e -> {
            mainFrame.setVisible(true); 
            dispose();
        });
        leftNavPanel.add(returnToMainButton);

        // 상단 버튼 기능
        listViewButton = new JButton("수업 목록 조회");
        listViewButton.addActionListener(e -> showView("ClassListView"));
        searchViewButton = new JButton("수업 검색");
        searchViewButton.addActionListener(e -> showView("ClassSearchView"));
        manageViewButton = new JButton("수업 관리");
        manageViewButton.addActionListener(e -> showView("ClassManageView"));

        rightNavPanel.add(listViewButton);
        rightNavPanel.add(searchViewButton);
        rightNavPanel.add(manageViewButton);

        navPanel.add(leftNavPanel, BorderLayout.WEST);
        navPanel.add(rightNavPanel, BorderLayout.EAST);

        add(navPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        setUpdateButtonVisible();
        updateUserInfoLabel();
    }

    /*
     *  로그인 상태에 따라 로그아웃 버튼으로 변경해줌
     */
    private void updateLoginLogoutButton() {
        if (loggedInUser != null) {
            loginLogoutButton.setText("로그아웃");
            loginLogoutButton.addActionListener(e -> logout());
        } else {
            loginLogoutButton.setText("로그인");
            loginLogoutButton.addActionListener(e -> showLogin());
        }
    }

    /*
     *  관리자 로그인 상태에만 '수업 관리' 버튼 보이게 함
     */
    private void setUpdateButtonVisible() {
        if (loggedInUser == null || "member".equals(loggedInUser.getRole())) {
            manageViewButton.setVisible(false);
        } else if ("employee".equals(loggedInUser.getRole())) {
            manageViewButton.setVisible(true);
        }
    }
    
    /**
     * 지정된 뷰를 표시합니다.
     * @param viewName 표시할 뷰의 이름
     */
    private void showView(String viewName) {
        cardLayout.show(mainPanel, viewName);
    }

    /*
     *  로그인 창 띄움
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
//                    refreshUI();
                    setLoggedInUser(loggedInUser);
                }
            }
        });
    }

    /*
     *  로그아웃
     */
    private void logout() {
        loggedInUser = null;
        getContentPane().removeAll();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        dispose();
    }

    /*
     *  수업 목록
     */
    private JPanel createClassListView() {
        JPanel panel = new JPanel(new GridLayout(1, 2));

        JPanel seodaemunPanel = new JPanel();
        JPanel gangnamPanel = new JPanel();

        seodaemunPanel.setLayout(new BoxLayout(seodaemunPanel, BoxLayout.Y_AXIS));
        gangnamPanel.setLayout(new BoxLayout(gangnamPanel, BoxLayout.Y_AXIS));

        seodaemunPanel.setBorder(BorderFactory.createTitledBorder("서대문점"));
        gangnamPanel.setBorder(BorderFactory.createTitledBorder("강남점"));

        JScrollPane seodaemunScrollPane = new JScrollPane(seodaemunPanel);
        JScrollPane gangnamScrollPane = new JScrollPane(gangnamPanel);

        panel.add(seodaemunScrollPane);
        panel.add(gangnamScrollPane);

        loadData(seodaemunPanel, gangnamPanel);

        return panel;
    }

    /*
     *  서대문구점, 강남점
     */
    private void loadData(JPanel seodaemunPanel, JPanel gangnamPanel) {
        List<ClassDTO> classes = classController.getAllClasses();

        for (ClassDTO classDTO : classes) {
            JButton classButton = new JButton(classDTO.getClassName());
            classButton.addActionListener(e -> showClassDetails(classDTO.getClassId()));

            if ("center01".equals(classDTO.getCenterId())) {
                seodaemunPanel.add(classButton);
            } else if ("center02".equals(classDTO.getCenterId())) {
                gangnamPanel.add(classButton);
            }
        }

        seodaemunPanel.revalidate();
        seodaemunPanel.repaint();
        gangnamPanel.revalidate();
        gangnamPanel.repaint();
    }

    /*
     *  수업 검색 화면
     */
    private JPanel createClassSearchView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTable table = new JTable();

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(5, 2));

        JTextField classNameField = new JTextField();
        JTextField instructorField = new JTextField();
        JComboBox<String> sportComboBox = new JComboBox<>(new String[]{"All", "헬스", "필라테스", "요가", "호신술", "수영"});
        JComboBox<String> dayComboBox = new JComboBox<>(new String[]{"All", "월수금", "화목", "주말", "매일"});
        JComboBox<String> centerComboBox = new JComboBox<>(new String[]{"All", "서대문점", "강남점"});

        searchPanel.add(new JLabel("수업 이름:"));
        searchPanel.add(classNameField);
        searchPanel.add(new JLabel("강사 이름:"));
        searchPanel.add(instructorField);
        searchPanel.add(new JLabel("스포츠 종류:"));
        searchPanel.add(sportComboBox);
        searchPanel.add(new JLabel("수업 요일:"));
        searchPanel.add(dayComboBox);
        searchPanel.add(new JLabel("소속 센터:"));
        searchPanel.add(centerComboBox);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchClasses(table, classNameField, instructorField, sportComboBox, dayComboBox, centerComboBox));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(searchButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    /*
     *  수업 검색
     */
    private void searchClasses(JTable table, JTextField classNameField, JTextField instructorField, JComboBox<String> sportComboBox, JComboBox<String> dayComboBox, JComboBox<String> centerComboBox) {
        String className = classNameField.getText();
        String instructorName = instructorField.getText();
        String sport = sportComboBox.getSelectedItem().toString();
        String day = dayComboBox.getSelectedItem().toString();
        String center = centerComboBox.getSelectedItem().toString();

        List<ClassDTO> classes = classController.searchClasses(
                className.isEmpty() || className.equals("All") ? null : className,
                instructorName.isEmpty() || instructorName.equals("All") ? null : instructorName,
                sport.equals("All") ? null : sport,
                day.equals("All") ? null : day,
                center.equals("All") ? null : center
        );

        String[] columnNames = {"수업명", "수강료", "설명", "최대 수강 인원"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (ClassDTO classDTO : classes) {
            Object[] row = {
                    classDTO.getClassName(),
                    classDTO.getClassFee(),
                    classDTO.getDescription(),
                    classDTO.getMaxPeople(),
            };
            model.addRow(row);
        }

        table.setModel(model);
    }

    /*
     *  수업 상세 정보 조회
     */
    public void showClassDetails(String classId) {
        ClassDTO classDTO = classController.getClassDetails(classId);
        if (classDTO != null) {
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

            JLabel classNameLabel = new JLabel("수업명: " + classDTO.getClassName());
            JLabel descriptionLabel = new JLabel("설명: " + classDTO.getDescription());
            JLabel classFeeLabel = new JLabel("수강료: " + classDTO.getClassFee());
            JLabel maxPeopleLabel = new JLabel("최대 수강 인원: " + classDTO.getMaxPeople());

            infoPanel.add(classNameLabel);
            infoPanel.add(descriptionLabel);
            infoPanel.add(classFeeLabel);
            infoPanel.add(maxPeopleLabel);

            panel.add(infoPanel, BorderLayout.CENTER);

            if (loggedInUser != null && "member".equals(loggedInUser.getRole())) {
                JButton enrollButton = new JButton("수강 신청");
                enrollButton.addActionListener(e -> showEnrollPage(classDTO));
                enrollButton.setPreferredSize(new Dimension(100, 30));

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(enrollButton);
                panel.add(buttonPanel, BorderLayout.SOUTH);
            }

            JDialog dialog = new JDialog(this, "수업 상세 정보", true);
            dialog.getContentPane().add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "수업을 찾을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     *  수강신청을 위해 추가 
     */
    private void showEnrollPage(ClassDTO classDTO) {
        if (loggedInUser == null) {
            JOptionPane.showMessageDialog(this, "먼저 로그인을 해주세요.", "로그인 필요", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        this.enrollmentController = new EnrollmentController(loggedInUser);

        JTextField monthField = new JTextField(10);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("수강 신청할 월 (YYYY-MM):"), BorderLayout.WEST);
        panel.add(monthField, BorderLayout.CENTER);

        
        int result = JOptionPane.showConfirmDialog(null, panel, "수강 신청", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String targetMonth = monthField.getText().trim();
            if (!targetMonth.isEmpty()) {
                try {
                    // 수강 신청 시도
                    boolean success = enrollmentController.enrollInClass(loggedInUser, classDTO.getClassId(), targetMonth);
                    if (success) {
                        // 수강 신청 성공 메시지
                        JOptionPane.showMessageDialog(null, "수강 신청이 완료되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // 수강 신청 실패 메시지
                        JOptionPane.showMessageDialog(null, "수강 신청에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    // SQL 예외 처리
                    JOptionPane.showMessageDialog(null, "데이터베이스 오류가 발생했습니다: " + e.getMessage(), "데이터베이스 오류", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // 잘못된 월 입력에 대한 오류 메시지
                JOptionPane.showMessageDialog(null, "올바른 월을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /*
     *  수업 관리 페이지
     */
    public void showManageView() {
        showView("ClassManageView");
    }

    /*
     *  로그인 상태 반영
     */
    public void setLoggedInUser(UserDTO loggedInUser) {
        this.loggedInUser = loggedInUser;
        setUpdateButtonVisible();
        updateUserInfoLabel();
        updateLoginLogoutButton();
    }

    /*
     *  사용자 표시
     */
    private void updateUserInfoLabel() {
        if (loggedInUser != null) {
            String role = "member".equals(loggedInUser.getRole()) ? "회원님" : "관리자님";
            userInfoLabel.setText(loggedInUser.getName() + " " + role);
        } else {
            userInfoLabel.setText("");
        }
    }

 /*
  *  다른 뷰 추가
  */
    public void addView(String name, JPanel panel) {
        mainPanel.add(panel, name);
    }
    
 /*
  *  다른 뷰로 전환
  */
    public void switchToView(String name) {
        showView(name);
    }
    
    /*
     * 수업 관리 뷰 새로고침 
     */
    public void refreshClassManageView() {
        if (classManageView instanceof ClassManageUI) {
            ((ClassManageUI) classManageView).loadData();
        }
    }
}
