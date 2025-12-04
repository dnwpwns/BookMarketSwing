package com.market.page;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.market.jdbc.DBUtil;
import com.market.member.UserDAO;

public class AdminUserPanel extends JPanel {

    private JTextField emailField, nameField;
    private JButton addUserBtn, deleteUserBtn, loadBtn;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public AdminUserPanel() {
        setLayout(new BorderLayout());

        // 상단 입력 영역
        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        emailField = new JTextField();
        nameField = new JTextField();
        addUserBtn = new JButton("회원 추가");
        deleteUserBtn = new JButton("회원 삭제");
        loadBtn = new JButton("회원 목록 불러오기");

        inputPanel.add(new JLabel("이메일"));
        inputPanel.add(emailField);
        inputPanel.add(addUserBtn);
        inputPanel.add(new JLabel("이름"));
        inputPanel.add(nameField);
        inputPanel.add(deleteUserBtn);

        add(inputPanel, BorderLayout.NORTH);

        // 회원 테이블
        String[] columnNames = { "이메일", "이름" };
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // 하단 버튼
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(loadBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // 이벤트 처리
        addUserBtn.addActionListener(e -> {
            String email = emailField.getText();
            String name = nameField.getText();
            if (UserDAO.insertUser(email, name)) {
                JOptionPane.showMessageDialog(this, "회원 추가 성공");
                loadUserList();
            } else {
                JOptionPane.showMessageDialog(this, "추가 실패 (중복 이메일?)");
            }
        });

        deleteUserBtn.addActionListener(e -> {
            String email = emailField.getText();
            int result = JOptionPane.showConfirmDialog(this, email + " 회원을 삭제할까요?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (UserDAO.deleteUser(email)) {
                    JOptionPane.showMessageDialog(this, "삭제 성공");
                    loadUserList();
                } else {
                    JOptionPane.showMessageDialog(this, "삭제 실패 (존재하지 않음)");
                }
            }
        });

        loadBtn.addActionListener(e -> loadUserList());
    }

    // 테이블에 회원 목록 로드
    private void loadUserList() {
        List<String[]> users = UserDAO.getAllUsers();
        tableModel.setRowCount(0); // 기존 행 제거
        for (String[] user : users) {
            tableModel.addRow(user);
        }
    }
}
