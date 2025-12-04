package com.market.page;

import java.sql.*;
import java.util.*;
import com.market.jdbc.DBConnection;

public class AdminBookDAO {

    public static boolean insertBook(String id, String name) {
        String sql = "INSERT INTO book (book_id, book_name) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteBook(String id) {
        String sql = "DELETE FROM book WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String[]> getAllBooks() {
        List<String[]> books = new ArrayList<>();
        String sql = "SELECT book_id, book_name FROM book";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                books.add(new String[]{rs.getString("book_id"), rs.getString("book_name")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
