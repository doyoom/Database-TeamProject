package com.powerFitness.ui;

import com.powerFitness.controller.ClassController;
import com.powerFitness.dto.ClassDTO;

import javax.swing.*;
import java.awt.*;

public class AddClassUI extends JPanel {
    private JTextField classIdField;
    private JTextField centerIdField;
    private JTextField instructorIdField;
    private JTextField ctypeIdField;
    private JTextField placeIdField;
    private JTextField ctimeIdField;
    private JTextField classNameField;
    private JTextField classFeeField;
    private JTextArea descriptionArea;
    private JTextField maxPeopleField;
    private JTextField currentPeopleField;
    private ClassController classController;
    private ClassUI classUI;

    /**
     * 수업 추가 UI 생성자
     * @param parentFrame 부모 프레임
     * @param classUI ClassUI 객체
     */
    public AddClassUI(JFrame parentFrame, ClassUI classUI) {
        this.classUI = classUI;
        classController = new ClassController();

        // 수업 추가 페이지
        setLayout(new GridLayout(14, 2));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("수업ID:"));
        classIdField = new JTextField();
        add(classIdField);

        add(new JLabel("센터ID:"));
        centerIdField = new JTextField();
        add(centerIdField);

        add(new JLabel("강사ID:"));
        instructorIdField = new JTextField();
        add(instructorIdField);

        add(new JLabel("수업유형ID:"));
        ctypeIdField = new JTextField();
        add(ctypeIdField);

        add(new JLabel("장소ID:"));
        placeIdField = new JTextField();
        add(placeIdField);

        add(new JLabel("수업시간ID:"));
        ctimeIdField = new JTextField();
        add(ctimeIdField);

        add(new JLabel("수업명:"));
        classNameField = new JTextField();
        add(classNameField);

        add(new JLabel("수강료:"));
        classFeeField = new JTextField();
        add(classFeeField);

        add(new JLabel("설명:"));
        descriptionArea = new JTextArea();
        add(descriptionArea);

        add(new JLabel("최대 수강 인원:"));
        maxPeopleField = new JTextField();
        add(maxPeopleField);

        add(new JLabel("현재 수강 인원:"));
        currentPeopleField = new JTextField();
        add(currentPeopleField);

        // '수업 추가' 버튼
        JButton addButton = new JButton("수업 추가");
        addButton.addActionListener(e -> {
            ClassDTO classDTO = new ClassDTO(
                    classIdField.getText(),
                    centerIdField.getText(),
                    instructorIdField.getText(),
                    ctypeIdField.getText(),
                    placeIdField.getText(),
                    ctimeIdField.getText(),
                    classNameField.getText(),
                    Integer.parseInt(classFeeField.getText()),
                    descriptionArea.getText(),
                    Integer.parseInt(maxPeopleField.getText()),
                    Integer.parseInt(currentPeopleField.getText())
            );
            // 수업 추가 실행
            classController.addClass(classDTO);
            JOptionPane.showMessageDialog(parentFrame, "수업이 추가되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
            classUI.switchToView("ClassManageView");
            classUI.refreshClassManageView(); // 관리 페이지 업데이트
        });
        add(addButton);

        // 'Go Back' 버튼
        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(e -> {
            classUI.switchToView("ClassManageView");
            classUI.refreshClassManageView();
        });
        add(backButton);
    }
}
