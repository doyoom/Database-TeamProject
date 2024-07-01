package com.powerFitness.ui.MemberUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import com.powerFitness.dao.AttendanceDAO;
import com.powerFitness.dao.MemberDAO;
import com.powerFitness.dao.PaymentDAO;

import com.powerFitness.dto.AttendanceDTO;
import com.powerFitness.dto.MemberDTO;
import com.powerFitness.dto.PaymentDTO;
import com.powerFitness.dto.UserDTO;

import com.powerFitness.ui.MemberAttendingUI;
import com.powerFitness.ui.MemberPaymentUI;

/**
 * 회원 프로필 UI 클래스입니다.
 */
public class MemberProfileUI extends JDialog {
    private MemberDTO member;

    /**
     * 회원 프로필 UI를 초기화합니다.
     * @param owner 부모 프레임
     * @param memberId 회원 ID
     * @param modal 모달 여부
     */
    public MemberProfileUI(Frame owner, String memberId, boolean modal) {
        super(owner, "회원 프로필", modal);
        MemberDAO memberDAO = new MemberDAO();
        this.member = memberDAO.getMemberById(memberId);

        initializeUI();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
    }


    /**
     * 회원 프로필 페이지 UI를 초기화합니다.
     */
    private void initializeUI() {

        setTitle("프로필 페이지");
        setSize(600, 600);
        setLayout(new BorderLayout());

        // 패널을 GridBagLayout으로 설정
        JPanel panel = new JPanel(new GridBagLayout());
        add(panel, BorderLayout.CENTER);

        // GridBagConstraints 설정을 메서드를 통해 가져옴
        GridBagConstraints gbc = createGridBagConstraints();
        
        addMemberInfo(panel, gbc); // 회원 정보 추가
        addButtonPanel(panel, gbc); // 수정 및 탈퇴 버튼 추가
    
        // "나의 수강내역" 버튼 추가
        JButton attendingHistoryButton = new JButton("나의 수강내역");
        attendingHistoryButton.addActionListener(e -> openMemberAttendingUI());
        
        gbc.gridy++;
        panel.add(attendingHistoryButton, gbc);
    
        // "나의 결제내역" 버튼 추가
        JButton paymentHistoryButton = new JButton("나의 결제내역");
        paymentHistoryButton.addActionListener(e -> openMemberPaymentUI());

        gbc.gridy++;
        panel.add(paymentHistoryButton, gbc);
    
        add(panel);
    }

    /**
     * MemberDTO를 UserDTO로 변환합니다.
     * @return 변환된 UserDTO 객체
     */
    private UserDTO createUserDTO() {
        UserDTO user = new UserDTO();
        user.setUserId(member.getMemberId());
        user.setCenterId(member.getCenterId());
        user.setName(member.getName());
        user.setRole("member");
        return user;
    }
    
    /**
     * GridBagConstraints를 생성합니다.
     * @return 생성된 GridBagConstraints 객체
     */
    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    /**
     * 수강 내역을 조회하는 창을 엽니다.
     */
    private void openMemberAttendingUI() {
        UserDTO user = createUserDTO(); // 공통 로직을 사용하여 UserDTO 생성
        MemberAttendingUI attendingUI = new MemberAttendingUI(this, user, true); // 부모 창과 모달 설정을 전달
        attendingUI.setVisible(true); // 외부에서 보이기 설정
    }
    
    /**
     * 결제 내역을 조회하는 창을 엽니다.
     */
    private void openMemberPaymentUI() {
        UserDTO user = createUserDTO();
        MemberPaymentUI paymentUI = new MemberPaymentUI(this, user, true); // MemberPaymentUI가 JDialog를 상속받도록 변경 필요
        paymentUI.setVisible(true);
    }

    /**
     * 회원 정보를 패널에 추가합니다.
     * @param panel 정보를 추가할 패널
     * @param gbc GridBagLayout의 Constraints
     */
    private void addMemberInfo(JPanel panel, GridBagConstraints gbc) {
        String[] labels = {"이름:", "전화번호:", "생일:", "성별:", "주소:", "센터명:", "비밀번호:"};
        String[] values = {member.getName(), member.getPhone(), String.valueOf(member.getBirthday()),
                           String.valueOf(member.getGender()), member.getAddress(), getCenterName(), member.getPassword()};
    
        gbc.gridwidth = 1; // 각 요소가 하나의 컬럼만 차지하도록 설정
        gbc.fill = GridBagConstraints.HORIZONTAL; // 가로로 꽉 차게 설정
    
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; // 라벨은 첫번째 컬럼에
            gbc.gridy = i; // 각 요소를 다른 행에 위치시킴
            panel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1; // 값은 두번째 컬럼에
            panel.add(new JLabel(values[i]), gbc);
        }
    }

    /**
     * 수정하기, 탈퇴 버튼을 포함한 패널을 추가합니다.
     * @param panel 버튼을 추가할 패널
     * @param gbc GridBagLayout의 Constraints
     */
    private void addButtonPanel(JPanel panel, GridBagConstraints gbc) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editButton = new JButton("수정하기");
        editButton.addActionListener(e -> openEditProfileDialog());

        // JButton deleteButton = new JButton("탈퇴");
        // deleteButton.addActionListener(e -> deleteMember());

        buttonPanel.add(editButton);
        // buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
    }

    /**
     * 센터명을 변환합니다.
     * @return 변환된 센터명
     */
    private String getCenterName() {
        return switch (member.getCenterId()) {
            case "center01" -> "파워피트니스";
            case "center02" -> "파워피트니스 강남점";
            default -> member.getCenterId();
        };
    }

    /**
     * 프로필 수정 다이얼로그를 엽니다.
     */
    private void openEditProfileDialog() {
        JDialog editDialog = createDialog("프로필 수정");
        JPanel editPanel = createEditPanel();
        populateEditPanel(editPanel, editDialog);
        editDialog.add(editPanel);
        editDialog.setVisible(true);
    }
    
    /**
     * 다이얼로그를 생성합니다.
     * @param title 다이얼로그 타이틀
     * @return 생성된 다이얼로그 객체
     */
    private JDialog createDialog(String title) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        return dialog;
    }
    
    /**
     * 수정 패널을 생성합니다.
     * @return 생성된 수정 패널
     */
    private JPanel createEditPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        return panel;
    }
    
    /**
     * 수정 패널을 채웁니다.
     * @param panel 패널
     * @param dialog 다이얼로그
     */
    private void populateEditPanel(JPanel panel, JDialog dialog) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
    
        JTextField phoneField = new JTextField(member.getPhone(), 20);
        JTextField passwordField = new JTextField(member.getPassword(), 20);
        JComboBox<String> centerComboBox = new JComboBox<>(new String[]{"파워피트니스", "파워피트니스 강남점"});
        centerComboBox.setSelectedIndex(member.getCenterId().equals("center02") ? 1 : 0);
    
        addToPanel(panel, new JLabel("전화번호:"), phoneField, gbc);
        gbc.gridy++;
        addToPanel(panel, new JLabel("비밀번호:"), passwordField, gbc);
        gbc.gridy++;
        addToPanel(panel, new JLabel("센터:"), centerComboBox, gbc);
    
        addButtons(panel, dialog, phoneField, passwordField, centerComboBox, gbc);
    }
    
    /**
     * 패널에 컴포넌트를 추가합니다.
     * @param panel 패널
     * @param label 라벨
     * @param field 필드
     * @param gbc GridBagLayout의 Constraints
     */
    private void addToPanel(JPanel panel, JLabel label, JComponent field, GridBagConstraints gbc) {
        panel.add(label, gbc);
        gbc.gridx++;
        panel.add(field, gbc);
        gbc.gridx = 0;
    }
    
    /**
     * 저장, 취소 버튼을 추가합니다.
     * @param panel 패널
     * @param dialog 다이얼로그
     * @param phoneField 전화번호 입력 필드
     * @param passwordField 비밀번호 입력 필드
     * @param centerComboBox 센터 콤보박스
     * @param gbc GridBagLayout의 Constraints
     */
    private void addButtons(JPanel panel, JDialog dialog, JTextField phoneField, JTextField passwordField, JComboBox<String> centerComboBox, GridBagConstraints gbc) {
        JButton saveButton = new JButton("저장");
        saveButton.addActionListener(e -> saveProfile(phoneField.getText(), passwordField.getText(), centerComboBox.getSelectedItem().toString(), dialog));
        JButton cancelButton = new JButton("취소");
        cancelButton.addActionListener(e -> dialog.dispose());
    
        gbc.gridy++;
        panel.add(saveButton, gbc);
        gbc.gridx++;
        panel.add(cancelButton, gbc);
    }
    
    /**
     * 프로필을 저장합니다.
     * @param phone 전화번호
     * @param password 비밀번호
     * @param center 센터명
     * @param dialog 다이얼로그
     */
    private void saveProfile(String phone, String password, String center, JDialog dialog) {
        if (password.isEmpty() || password.length() < 8) {
            JOptionPane.showMessageDialog(this, "비밀번호는 8자 이상이어야 합니다.", "유효성 검사 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        member.setPhone(phone);
        member.setPassword(password);
        member.setCenterId(center.equals("파워피트니스") ? "center01" : "center02");
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.updateMember(member);
        dialog.dispose();
        refreshProfile();
    }

    /**
     * 프로필을 새로고칩니다.
     */
    private void refreshProfile() {
        getContentPane().removeAll();
        initializeUI();
        revalidate();
        repaint();
    }
    
}
