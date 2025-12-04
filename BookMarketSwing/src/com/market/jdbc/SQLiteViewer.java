package com.market.jdbc;

import java.sql.*;

public class SQLiteViewer {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:market.db";  // market.db 경로 확인 필요

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // 컬럼 정보 출력
            String sql = "PRAGMA table_info(user_information);";
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("📋 user_information 테이블 컬럼 정보:");
            while (rs.next()) {
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String type = rs.getString("type");
                int notnull = rs.getInt("notnull");
                String dfltValue = rs.getString("dflt_value");
                int pk = rs.getInt("pk");

                System.out.printf(" - %s (%s) %s %s %s\n",
                        name,
                        type,
                        notnull == 1 ? "NOT NULL" : "",
                        dfltValue != null ? "DEFAULT " + dfltValue : "",
                        pk == 1 ? "[PK]" : ""
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
