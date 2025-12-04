package com.market.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class DBUtil {
    private static final String DB_PATH = "market.db";  // jar와 같은 위치에 있어야 함

    public static Connection getConnection() {
        try {
            // 현재 디렉토리 출력 (디버깅용)
            System.out.println("작업 디렉토리: " + new File(".").getAbsolutePath());

            // SQLite 드라이버 로딩
            Class.forName("org.sqlite.JDBC");

            // DB 연결
            String url = "jdbc:sqlite:" + DB_PATH;
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ SQLite 연결 실패");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        System.out.println(conn != null ? "✅ SQLite 연결 성공" : "❌ SQLite 연결 실패");
    }
}
