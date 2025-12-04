package com.market.member;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import com.market.jdbc.DBConnection;
import com.market.jdbc.DBUtil;

public class UserDAO {

    // 회원가입
    public static boolean register(String email, String password, String name, String phone, String address, String better_address) {
        String sql = "INSERT INTO user_information (email, password, name, phone, address, better_address, reg_date, purchase, total_spent) VALUES (?, ?, ?, ?, ?, ?, ?, '', 0)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setString(4, phone);
            pstmt.setString(5, address);
            pstmt.setString(6, better_address);
            pstmt.setString(7, LocalDateTime.now().toString()); // ✅ 현재 시간

            return pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 로그인
    public static boolean login(String email, String password) {
        String sql = "SELECT * FROM user_information WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 회원정보 조회
    public static void getUserInfo(String email) {
        String sql = "SELECT * FROM user_information WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("이메일: " + rs.getString("email"));
                System.out.println("이름: " + rs.getString("name"));
                System.out.println("연락처: " + rs.getString("phone"));
                System.out.println("주소: " + rs.getString("address"));
                System.out.println("상세주소: " + rs.getString("better_address"));
                System.out.println("쓴 돈: " + rs.getInt("total_spent"));
                System.out.println("구매내역: " + rs.getString("purchase"));
                System.out.println("가입일: " + rs.getString("reg_date"));
            } else {
                System.out.println("회원 정보가 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<String[]> getAllUsers() {
        List<String[]> userList = new ArrayList<>();
        String sql = "SELECT email, name FROM user_information";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                userList.add(new String[]{ rs.getString("email"), rs.getString("name") });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
 // UserDAO.java 또는 AdminDAO.java에 추가
    public static boolean insertUser(String email, String name) {
        String sql = "INSERT INTO user_information (email, password, name, reg_date, phone, address, better_address, purchase, total_spent) " +
                     "VALUES (?, '', ?, ?, '', '', '', '', 0)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, name);
            pstmt.setString(3, java.time.LocalDateTime.now().toString());
            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteUser(String email) {
        String sql = "DELETE FROM user_information WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
