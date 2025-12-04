package com.market.page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminLoginDialog extends JDialog {

    private JTextField idField;
    private JPasswordField pwField;
    public boolean isLogin = false;
    private JFrame parentFrame; // ✅ 부모 프레임 저장

    public AdminLoginDialog(JFrame frame, String str) {
        super(frame, "관리자 로그인", true);
        this.parentFrame = frame; // ✅ 저장

        Font ft = new Font("함초롬돋움", Font.BOLD, 15);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - 400) / 2, (screenSize.height - 300) / 2);
        setSize(400, 300);
        setLayout(null);

        // 제목
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(0, 20, 400, 50);
        JLabel titleLabel = new JLabel("관리자 로그인");
        titleLabel.setFont(new Font("함초롬돋움", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel);

        // ID 입력
        JPanel idPanel = new JPanel();
        idPanel.setBounds(0, 70, 400, 50);
        JLabel idLabel = new JLabel("아 이 디 : ");
        idLabel.setFont(ft);
        idField = new JTextField(10);
        idField.setFont(ft);
        idPanel.add(idLabel);
        idPanel.add(idField);
        add(idPanel);

        // 비밀번호 입력
        JPanel pwPanel = new JPanel();
        pwPanel.setBounds(0, 120, 400, 50);
        JLabel pwLabel = new JLabel("비밀번호 : ");
        pwLabel.setFont(ft);
        pwField = new JPasswordField(10);
        pwField.setFont(ft);
        pwPanel.add(pwLabel);
        pwPanel.add(pwField);
        add(pwPanel);

        // 버튼
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 170, 400, 50);
        JButton okButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");
        okButton.setFont(ft);
        cancelButton.setFont(ft);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);

        // ✅ 확인 버튼: 하드코딩된 관리자 계정으로 검사
        okButton.addActionListener(e -> {
            String inputId = idField.getText().trim();
            String inputPw = new String(pwField.getPassword()).trim();

            // 하드코딩된 관리자 계정 정보
            String adminId = "admin";
            String adminPw = "1234";

            if (inputId.equals(adminId) && inputPw.equals(adminPw)) {
                isLogin = true;
                JOptionPane.showMessageDialog(this, "✅ 관리자 로그인 성공");
                dispose(); // 현재 로그인 다이얼로그 닫기

                // ✅ 관리자 메뉴 다이얼로그 열기
                new AdminChoiceDialog(parentFrame);

            } else {
                JOptionPane.showMessageDialog(this, "❌ 관리자 정보가 일치하지 않습니다");
            }
        });

        // 취소 버튼
        cancelButton.addActionListener(e -> {
            isLogin = false;
            dispose();
        });
    }
}
