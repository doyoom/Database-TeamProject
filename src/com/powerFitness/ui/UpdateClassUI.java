package com.powerFitness.ui;

import com.powerFitness.controller.ClassController;
import com.powerFitness.dto.ClassDTO;

import javax.swing.*;
import java.awt.*;

/**
 * UpdateClassUI 클래스는 수업 정보를 수정하는 사용자 인터페이스를 제공합니다.
 */
public class UpdateClassUI extends JPanel {
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
     * UpdateClassUI 생성자.
     *
     * @param parentFrame 부모 JFrame
     * @param classDTO    수정할 수업 정보를 담은 ClassDTO 객체
     * @param classUI     ClassUI 인스턴스
     */
    public UpdateClassUI(JFrame parentFrame, ClassDTO classDTO, ClassUI classUI) {
        this.classUI = classUI;
        classController = new ClassController();

        // 수업 수정 페이지 레이아웃 설정
        setLayout(new GridLayout(14, 2));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 각 필드와 레이블 추가
        add(new JLabel("수업ID:"));
        classIdField = new JTextField(classDTO.getClassId());
        classIdField.setEditable(false);
        add(classIdField);

        add(new JLabel("센터ID:"));
        centerIdField = new JTextField(classDTO.getCenterId());
        add(centerIdField);

        add(new JLabel("강사ID:"));
        instructorIdField = new JTextField(classDTO.getInstructorId());
        add(instructorIdField);

        add(new JLabel("수업유형ID:"));
        ctypeIdField = new JTextField(classDTO.getCtypeId());
        add(ctypeIdField);

        add(new JLabel("장소ID:"));
        placeIdField = new JTextField(classDTO.getPlaceId());
        add(placeIdField);

        add(new JLabel("수업시간ID:"));
        ctimeIdField = new JTextField(classDTO.getCtimeId());
        add(ctimeIdField);

        add(new JLabel("수업명:"));
        classNameField = new JTextField(classDTO.getClassName());
        add(classNameField);

        add(new JLabel("수강료:"));
        classFeeField = new JTextField(String.valueOf(classDTO.getClassFee()));
        add(classFeeField);

        add(new JLabel("설명:"));
        descriptionArea = new JTextArea(classDTO.getDescription());
        add(descriptionArea);

        add(new JLabel("최대 수강 인원:"));
        maxPeopleField = new JTextField(String.valueOf(classDTO.getMaxPeople()));
        add(maxPeopleField);

        add(new JLabel("현재 수강 인원:"));
        currentPeopleField = new JTextField(String.valueOf(classDTO.getCurrentPeople()));
        add(currentPeopleField);

        // '수업 수정' 버튼 기능 추가
        JButton updateButton = new JButton("수업 수정");
        updateButton.addActionListener(e -> {
            // ClassDTO 객체 업데이트
            classDTO.setCenterId(centerIdField.getText());
            classDTO.setInstructorId(instructorIdField.getText());
            classDTO.setCtypeId(ctypeIdField.getText());
            classDTO.setPlaceId(placeIdField.getText());
            classDTO.setCtimeId(ctimeIdField.getText());
            classDTO.setClassName(classNameField.getText());
            classDTO.setClassFee(Integer.parseInt(classFeeField.getText()));
            classDTO.setDescription(descriptionArea.getText());
            classDTO.setMaxPeople(Integer.parseInt(maxPeopleField.getText()));
            classDTO.setCurrentPeople(Integer.parseInt(currentPeopleField.getText()));
            
            // 수업 수정 실행
            classController.updateClass(classDTO);
            
            // 성공 메시지 표시
            JOptionPane.showMessageDialog(parentFrame, "수업이 수정되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
            
            // 관리 페이지로 전환
            classUI.switchToView("ClassManageView");
            classUI.refreshClassManageView();
        });
        add(updateButton);

        // 'Go Back' 버튼 기능 추가
        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(e -> {
            classUI.switchToView("ClassManageView");
            classUI.refreshClassManageView(); 
        });
        add(backButton);
    }
}
