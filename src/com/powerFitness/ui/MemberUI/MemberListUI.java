package com.powerFitness.ui.MemberUI;

import com.powerFitness.controller.MemberController;
import com.powerFitness.dto.MemberDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MemberListUI extends JDialog {

    private JTable table;
    private JTextField searchField;
    private MemberController memberController;
    private DefaultTableModel tableModel;

    /**
     * 회원 목록 UI를 생성합니다.
     *
     * @param owner 부모 프레임
     * @param modal 모달 여부
     */
    public MemberListUI(Frame owner, boolean modal) {
        super(owner, "회원 목록", modal);
        this.memberController = new MemberController();
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("회원 목록", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("검색:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("검색");
        searchButton.addActionListener(e -> searchMembers());

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.SOUTH);

        // 회원 목록 가져오기
        List<MemberDTO> members = memberController.getAllMembers();

        // 테이블 설정
        String[] columnNames = {"Member ID", "Center ID", "Name", "Phone", "Gender", "Join Date", "Quit Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12)); // 테이블 폰트 설정
        table.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 12)); // 테이블 헤더 폰트 설정

        updateTable(members);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 프레임을 화면에 표시
        setVisible(true);
    }

    /**
     * 테이블을 갱신합니다.
     *
     * @param members 회원 목록
     */
    private void updateTable(List<MemberDTO> members) {
        tableModel.setRowCount(0);
        for (MemberDTO member : members) {
            Object[] rowData = {
                member.getMemberId(),
                convertCenterId(member.getCenterId()),
                member.getName(),
                member.getPhone(),
                member.getGender(),
                member.getJoinDate(),
                member.getQuitDate()
            };
            tableModel.addRow(rowData);
        }
    }

    /**
     * 회원을 검색합니다.
     */
    private void searchMembers() {
        String keyword = searchField.getText().trim();
        List<MemberDTO> members = memberController.searchMembers(keyword);
        updateTable(members);
    }

    /**
     * 센터 ID를 명칭으로 변환합니다.
     *
     * @param centerId 센터 ID
     * @return 센터 명칭
     */
    private String convertCenterId(String centerId) {
        switch (centerId) {
            case "center01": return "서대문점";
            case "center02": return "강남점";
            default: return centerId;
        }
    }
}
