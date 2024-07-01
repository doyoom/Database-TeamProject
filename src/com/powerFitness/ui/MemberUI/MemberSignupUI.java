package com.powerFitness.ui.MemberUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

import javax.swing.*;
import java.awt.*;

import com.powerFitness.controller.MemberController;
import com.powerFitness.dto.MemberDTO;

/**
 * 회원 가입 UI를 담당하는 클래스입니다.
 */
public class MemberSignupUI extends JDialog{

    private JTextField nameField, phoneField, addressField, birthdayField;
    private JPasswordField passwordField;
    private JComboBox<String> centerComboBox, genderComboBox;
    private JButton signUpButton;


    private MemberController memberController;

    /**
     * 회원 가입 UI의 생성자입니다.
     * @param owner 부모 프레임
     */
    public MemberSignupUI(Frame owner) {
        super(owner, "회원 가입", true);
        memberController = new MemberController();
        initializeUI();
        setSize(400, 300);
        setLocationRelativeTo(owner);
    }

    /**
     * UI를 초기화합니다.
     */
    private void initializeUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;  // 추가 
        gbc.weightx = 1; // 가로 방향
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 라벨용 그리드 너비

        add(new JLabel("이     름 :"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("연  락 처 :"), gbc);
        phoneField = new JTextField(15);
        gbc.gridx = 1;
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("주     소 :"), gbc);
        addressField = new JTextField(15);
        gbc.gridx = 1;
        add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("생년월일 :"), gbc);
        birthdayField = new JTextField(15);
        gbc.gridx = 1;
        add(birthdayField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("비밀번호 :"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("등록센터 :"), gbc);
        centerComboBox = new JComboBox<>(new String[]{"파워피트니스 서대문점", "파워피트니스 강남점"});
        gbc.gridx = 1;
        add(centerComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("성     별 :"), gbc);
        genderComboBox = new JComboBox<>(new String[]{"남성", "여성"});
        gbc.gridx = 1;
        add(genderComboBox, gbc);

        signUpButton = new JButton("가입하기");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(signUpButton, gbc);

        signUpButton.addActionListener(this::signUp);
    }

    /**
     * 회원 가입 버튼 이벤트 처리 메서드입니다.
     * @param e 액션 이벤트 객체
     */
    private void signUp(ActionEvent e) {
        try {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            String birthday = birthdayField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String center = (String) centerComboBox.getSelectedItem();
            String gender = (String) genderComboBox.getSelectedItem();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || birthday.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 채워주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convert center and gender to database IDs or codes
            String centerId = center.equals("파워피트니스 서대문점") ? "center01" : "center02";
            char genderChar = gender.equals("남성") ? 'M' : 'F';

            MemberDTO member = new MemberDTO();
            member.setCenterId(centerId);
            
            // Generate the next member ID
            String lastMemberId = memberController.getLastMemberId(centerId);
            int newIdNumber = Integer.parseInt(lastMemberId.substring(7)) + 1;
            String memberId = "member" + String.format("%05d", newIdNumber);
            
            Date joinDate = Date.valueOf(LocalDate.now()); // Assuming the SQL Date import
            
//            MemberDTO member = new MemberDTO(memberId, name, phone, address, Date.valueOf(birthday), password, centerId, genderChar, joinDate);
         // DTO 객체 생성
            member.setMemberId(memberId);
            member.setName(name);
            member.setPhone(phone);
            member.setAddress(address);
            member.setBirthday(Date.valueOf(birthday)); // String을 Date로 변환
            member.setPassword(password);
            member.setGender(genderChar);
            member.setJoinDate(joinDate);
            memberController.registerMember(member);
            JOptionPane.showMessageDialog(this, "회원 가입 성공!");
            		} catch (Exception ex) {
            		JOptionPane.showMessageDialog(this, "회원 가입 실패: " + ex.getMessage(), "회원 가입 오류", JOptionPane.ERROR_MESSAGE);
            		}
            		}
            		}
