package com.powerFitness.ui;

import com.powerFitness.MainFrame;
import com.powerFitness.controller.ClassController;
import com.powerFitness.dto.ClassDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 수업을 관리하는 UI 클래스입니다.
 */
public class ClassManageUI extends JPanel {
    private JTable table;
    private ClassController classController;
    private MainFrame mainFrame;
    private ClassUI classUI;

    /**
     * ClassManageUI 클래스의 생성자입니다.
     * @param mainFrame 메인 프레임
     * @param classUI 수업 UI
     */
    public ClassManageUI(MainFrame mainFrame, ClassUI classUI) {
        this.mainFrame = mainFrame;
        this.classUI = classUI;

        // 수업 관리 페이지
        classController = new ClassController();
        setLayout(new BorderLayout());

        // 상단 패널 (메인 화면 버튼 추가)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton returnToMainButton = new JButton("메인 화면");
        returnToMainButton.addActionListener(e -> {
            mainFrame.setVisible(true); 
            classUI.dispose();
        });
        topPanel.add(returnToMainButton);

        // 수업 목록 테이블
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // '수업 추가' 버튼
        JButton addButton = new JButton("수업 추가");
        addButton.addActionListener(e -> {
            AddClassUI addClassUI = new AddClassUI(mainFrame, classUI);
            classUI.addView("AddClassUI", addClassUI);
            classUI.switchToView("AddClassUI");
        });
        buttonPanel.add(addButton);

        // '수업 수정' 버튼
        JButton updateButton = new JButton("수업 수정");
        updateButton.addActionListener(e -> {
            String classId = JOptionPane.showInputDialog(mainFrame, "수정할 수업의 ID를 입력하세요:");
            if (classId != null && !classId.trim().isEmpty()) {
                ClassDTO classDTO = classController.getClassDetails(classId);
                if (classDTO != null) {
                    UpdateClassUI updateClassUI = new UpdateClassUI(mainFrame, classDTO, classUI);
                    classUI.addView("UpdateClassUI", updateClassUI);
                    classUI.switchToView("UpdateClassUI");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "수업을 찾을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(updateButton);

        // '수업 삭제' 버튼
        JButton deleteButton = new JButton("수업 삭제");
        deleteButton.addActionListener(e -> {
            String classId = JOptionPane.showInputDialog(mainFrame, "삭제할 수업의 ID를 입력하세요:");
            if (classId != null && !classId.trim().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(mainFrame, "정말로 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // 수업 삭제 실행
                    classController.deleteClass(classId);
                    JOptionPane.showMessageDialog(mainFrame, "삭제되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                }
            }
        });
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
        loadData();
    }

    /**
     * 수업 데이터를 로드하여 테이블에 표시합니다.
     */
    public void loadData() {
        List<ClassDTO> classes = classController.getAllClasses();
        String[] columnNames = {"수업ID", "센터ID", "강사ID", "수업유형ID", "장소ID", "수업시간ID", "수업명", "수강료", "설명", "최대 수강 인원", "현재 수강 인원"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 모든 셀을 수정할 수 없도록 설정
            }
        };

        for (ClassDTO classDTO : classes) {
            Object[] row = {
                    classDTO.getClassId(),
                    classDTO.getCenterId(),
                    classDTO.getInstructorId(),
                    classDTO.getCtypeId(),
                    classDTO.getPlaceId(),
                    classDTO.getCtimeId(),
                    classDTO.getClassName(),
                    classDTO.getClassFee(),
                    classDTO.getDescription(),
                    classDTO.getMaxPeople(),
                    classDTO.getCurrentPeople()
            };
            model.addRow(row);
        }

        table.setModel(model);
    }
}
