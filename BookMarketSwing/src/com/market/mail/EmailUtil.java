package com.market.mail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	 private static final String FROM_EMAIL = "dong98724@gmail.com";
	    private static final String APP_PASSWORD = "kgnkqncndnwmoodz";

    public static void send(String toEmail, String code) {
        Properties props = new Properties();

        // ✅ 여기에 작성합니다
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("[BookMarket] 이메일 인증번호");
            message.setText("인증번호: " + code);

            Transport.send(message);
            System.out.println("이메일 전송 완료: " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("이메일 전송 실패: " + e.getMessage());
        }
    }
}
