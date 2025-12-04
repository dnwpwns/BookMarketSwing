package com.market.page;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.market.member.User;
import com.market.member.UserInIt;

public class GuestInfoPage extends JPanel {

	public GuestInfoPage(JPanel panel) {
		Font ft = new Font("함초롬돋움", Font.BOLD, 15);
		setLayout(null);

		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());

		User user = UserInIt.getmUser(); // 로그인한 사용자 정보 가져오기

		if (user == null) {
			JLabel errorLabel = new JLabel("❌ 사용자 정보가 존재하지 않습니다.");
			errorLabel.setFont(ft);
			errorLabel.setBounds(50, 100, 500, 30);
			add(errorLabel);
			return;
		}

		int y = 50;

		addLabelPair("이메일 : ", user.getEmail(), y += 50, ft);
		addLabelPair("이름 : ", user.getName(), y += 50, ft);
		addLabelPair("연락처 : ", user.getPhone(), y += 50, ft);
		addLabelPair("주소 : ", user.getAddress(), y += 50, ft);
		addLabelPair("상세주소 : ", user.getBetterAddress(), y += 50, ft);

		// 가입일 형식 변경
		String formattedDate = user.getRegDate();
		try {
			LocalDateTime dateTime = LocalDateTime.parse(user.getRegDate());
			formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		} catch (Exception e) {
			// 실패 시 원본 문자열 그대로 사용
		}
		addLabelPair("가입일 : ", formattedDate, y += 50, ft);

		addLabelPair("구매내역 : ", String.valueOf(user.getPurchase()), y += 50, ft);
		addLabelPair("총 지출액 : ", user.getTotalSpent() + "원", y += 50, ft);
	}

	private void addLabelPair(String labelText, String valueText, int y, Font ft) {
		JLabel label = new JLabel(labelText);
		label.setFont(ft);
		label.setBounds(50, y, 150, 30);
		add(label);

		JLabel value = new JLabel(valueText);
		value.setFont(ft);
		value.setBounds(200, y, 600, 30);
		add(value);
	}

	// int 값을 받아서 String으로 변환하는 오버로딩 메서드
	private void addLabelPair(String labelText, int valueInt, int y, Font ft) {
		addLabelPair(labelText, String.valueOf(valueInt), y, ft);
	}
}
