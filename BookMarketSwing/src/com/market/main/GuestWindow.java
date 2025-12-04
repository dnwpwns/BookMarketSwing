package com.market.main;

import com.market.page.RegisterDialog;
import javax.swing.*;
import java.awt.*;
import com.market.member.UserInIt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.market.jdbc.DBConnection;
import com.market.member.User;

public class GuestWindow extends JFrame {

	public GuestWindow(String title, int x, int y, int width, int height) {
		initContainer(title, x, y, width, height);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("./images/shop.png").getImage());
	}

	private void initContainer(String title, int x, int y, int width, int height) {
		setTitle(title);
		setBounds(x, y, width, height);
		setLayout(null);

		Font ft = new Font("함초롬돋움", Font.BOLD, 15);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - 1000) / 2, (screenSize.height - 750) / 2);

		JPanel userPanel = new JPanel();
		userPanel.setBounds(0, 100, 1000, 256);

		ImageIcon imageIcon = new ImageIcon("./images/user.png");
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH));
		JLabel userLabel = new JLabel(imageIcon);
		userPanel.add(userLabel);
		add(userPanel);

		JPanel titlePanel = new JPanel();
		titlePanel.setBounds(0, 350, 1000, 50);
		add(titlePanel);

		JLabel titleLabel = new JLabel("-- 고객 정보를 입력하세요 --");
		titleLabel.setFont(ft);
		titleLabel.setForeground(Color.BLUE);
		titlePanel.add(titleLabel);

		JPanel namePanel = new JPanel();
		namePanel.setBounds(0, 400, 1000, 50);
		add(namePanel);

		JLabel nameLabel = new JLabel("       Email : ");
		nameLabel.setFont(ft);
		namePanel.add(nameLabel);

		JTextField nameField = new JTextField(16);
		nameField.setFont(ft);
		namePanel.add(nameField);

		JPanel phonePanel = new JPanel();
		phonePanel.setBounds(0, 450, 1000, 50);
		add(phonePanel);

		JLabel phoneLabel = new JLabel("PassWord : ");
		phoneLabel.setFont(ft);
		phonePanel.add(phoneLabel);

		JPasswordField phoneField = new JPasswordField(16);
		phoneField.setFont(ft);
		phonePanel.add(phoneField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 500, 1000, 100);
		add(buttonPanel);

		// 로그인 버튼 (왼쪽)
		JButton loginButton = new JButton("로그인");
		loginButton.setFont(ft);
		loginButton.setPreferredSize(new Dimension(120, 30));
		buttonPanel.add(loginButton);

		ActionListener loginAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 여기에 수정
				String inputEmail = nameField.getText().trim();
				String inputPw = new String(phoneField.getPassword()).trim();

				if (inputEmail.isEmpty() || inputPw.isEmpty()) {
					JOptionPane.showMessageDialog(loginButton, "Email와 Password를 입력하세요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 이 부분 전체를 네가 준 코드로 바꾸면 됨
				String sql = "SELECT * FROM user_information WHERE email = ? AND password = ?";
				try (Connection conn = DBConnection.getConnection();
					 PreparedStatement ps = conn.prepareStatement(sql)) {

					ps.setString(1, inputEmail);
					ps.setString(2, inputPw);
					ResultSet rs = ps.executeQuery();

					if (rs.next()) {
						User user = new User(
							    rs.getString("email"),
							    rs.getString("password"),
							    rs.getString("name"),
							    rs.getString("phone"),
							    rs.getString("reg_date"),
							    rs.getString("address"),
							    rs.getString("better_address"),
							    rs.getInt("purchase"),
							    rs.getInt("total_spent")
							);
							UserInIt.setmUser(user);

						JOptionPane.showMessageDialog(loginButton, "✅ 로그인 성공");
						dispose();
						new MainWindow("온라인 서점", 0, 0, 1000, 750);
					} else {
						JOptionPane.showMessageDialog(loginButton, "❌ ID 또는 비밀번호가 올바르지 않습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(loginButton, "데이터베이스 오류: " + ex.getMessage(), "DB 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		};

		loginButton.addActionListener(loginAction);
		nameField.addActionListener(loginAction);
		phoneField.addActionListener(loginAction);

		// 회원가입 버튼 (오른쪽)
		JButton registerButton = new JButton("회원가입");
		registerButton.setFont(ft);
		registerButton.setPreferredSize(new Dimension(120, 30));
		buttonPanel.add(registerButton);

		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegisterDialog(GuestWindow.this);
			}
		});
	}
}
