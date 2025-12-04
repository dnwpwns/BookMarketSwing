package com.market.page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Random;
import com.market.member.UserDAO;
import com.market.mail.EmailUtil;

public class RegisterDialog extends JDialog {
    private String generatedCode = null;

    public RegisterDialog(JFrame parent) {
        super(parent, "회원가입", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font ft = new Font("함초롬돋움", Font.PLAIN, 14);

        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField codeField = new JTextField(10);
        JPasswordField pwField = new JPasswordField(15);
        JPasswordField pwCheckField = new JPasswordField(15);
        JTextField addressField = new JTextField(15);
        JTextField detailAddressField = new JTextField(15);
        JTextField phoneField = new JTextField(15); // 🔹 전화번호 필드 추가
        JButton addressSearchBtn = new JButton("우편번호 검색");

        JButton sendCodeButton = new JButton("인증번호 보내기");
        JButton verifyCodeButton = new JButton("인증번호 확인");

        JLabel pwStatusLabel = new JLabel(" ");
        pwStatusLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        pwStatusLabel.setVisible(false);

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("이름:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("이메일:"), gbc);
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        emailPanel.add(emailField);
        emailPanel.add(sendCodeButton);
        gbc.gridx = 1; gbc.gridy = 1; add(emailPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("인증번호:"), gbc);
        JPanel codePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        codePanel.add(codeField);
        codePanel.add(verifyCodeButton);
        gbc.gridx = 1; gbc.gridy = 2; add(codePanel, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("비밀번호:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; add(pwField, gbc);
        gbc.gridx = 2; gbc.gridy = 3; add(pwStatusLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        JLabel pwGuideLabel = new JLabel("※ 최소 8자 이상, 공백 없음, 특수문자 포함");
        pwGuideLabel.setForeground(Color.GRAY);
        pwGuideLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        add(pwGuideLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 5; add(new JLabel("비밀번호 확인:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; add(pwCheckField, gbc);

        gbc.gridx = 0; gbc.gridy = 6; add(new JLabel("주소:"), gbc);
        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        addressPanel.add(addressField);
        addressPanel.add(addressSearchBtn);
        gbc.gridx = 1; gbc.gridy = 6; add(addressPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 7; add(new JLabel("상세주소:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; add(detailAddressField, gbc);

        gbc.gridx = 0; gbc.gridy = 8; add(new JLabel("전화번호:"), gbc);
        gbc.gridx = 1; gbc.gridy = 8; add(phoneField, gbc);

        JButton registerBtn = new JButton("회원가입");
        JButton cancelBtn = new JButton("취소");
        gbc.gridx = 1; gbc.gridy = 9; add(registerBtn, gbc);
        gbc.gridx = 2; gbc.gridy = 9; add(cancelBtn, gbc);

        pwField.getDocument().addDocumentListener(new DocumentListener() {
            public void update() {
                String pw = new String(pwField.getPassword());
                pwStatusLabel.setVisible(!pw.isEmpty());
                if (pw.length() <= 8) {
                    pwStatusLabel.setText("8자 이하");
                    pwStatusLabel.setForeground(Color.RED);
                } else if (pw.length() <= 12) {
                    pwStatusLabel.setText("적정");
                    pwStatusLabel.setForeground(new Color(0, 153, 0));
                } else {
                    pwStatusLabel.setText("완벽");
                    pwStatusLabel.setForeground(Color.BLUE);
                }
            }
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
        });

        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String pw = new String(pwField.getPassword());
            String pwCheck = new String(pwCheckField.getPassword());
            String address = addressField.getText();
            String betterAddress = detailAddressField.getText();
            String phone = phoneField.getText(); // 🔹 전달

            // ✅ 추가된 유효성 검사
            if (name.isEmpty() || email.isEmpty() || address.isEmpty() || betterAddress.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 항목을 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (pw.length() < 8 || pw.contains(" ") || !pw.matches(".*[!@#$%^&*()_+=\\-\\[\\]{}|;:'\",.<>/?`~].*")) {
                JOptionPane.showMessageDialog(this, "비밀번호 조건을 확인하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pw.equals(pwCheck)) {
                JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = UserDAO.register(email, pw, name, phone, address, betterAddress);
            if (success) {
                JOptionPane.showMessageDialog(this, "회원가입 완료!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "회원가입 실패", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        sendCodeButton.addActionListener(e -> {
            String toEmail = emailField.getText();
            if (toEmail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "이메일을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
            generatedCode = String.format("%06d", new Random().nextInt(1000000));
            EmailUtil.send(toEmail, generatedCode);
            JOptionPane.showMessageDialog(this, "인증번호가 이메일로 전송되었습니다.");
        });

        verifyCodeButton.addActionListener(e -> {
            String inputCode = codeField.getText();
            if (generatedCode != null && generatedCode.equals(inputCode)) {
                JOptionPane.showMessageDialog(this, "인증 완료");
            } else {
                JOptionPane.showMessageDialog(this, "인증 실패", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        addressSearchBtn.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://www.epost.go.kr/search.RetrieveIntegrationNewZipCdList.comm"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setSize(600, 530);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
