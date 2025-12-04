package com.market.page;

import javax.swing.*;
import java.awt.*;
import com.market.page.AdminPage;

public class AdminChoiceDialog extends JDialog {

    public AdminChoiceDialog(JFrame parent) {
        super(parent, "관리자 기능 선택", true);
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton userManageBtn = new JButton("회원 관리");
        JButton bookManageBtn = new JButton("도서 관리");

        userManageBtn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        bookManageBtn.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        add(userManageBtn);
        add(bookManageBtn);

        userManageBtn.addActionListener(e -> {
            dispose();  // 이 창 닫고
            JFrame userFrame = new JFrame("회원 관리");
            userFrame.setContentPane(new AdminUserPanel());
            userFrame.setSize(400, 300);
            userFrame.setLocationRelativeTo(null);
            userFrame.setVisible(true);
        });

        bookManageBtn.addActionListener(e -> {
            dispose();
            // AdminPage는 JPanel이므로 JFrame에 붙여서 실행
            JFrame parentFrame = (JFrame) getParent();

            // JFrame의 contentPane 가져오기
            Container content = parentFrame.getContentPane();

            // 기존 패널 제거
            content.removeAll();

            // 새로운 AdminPage 붙이기
            AdminPage adminPage = new AdminPage((JPanel) content);
            content.add(adminPage);

            // 레이아웃 새로 고침
            content.revalidate();
            content.repaint();
            
            });

        setSize(300, 200);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
