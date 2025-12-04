package com.market.bookitem;

import java.io.*;
import java.util.ArrayList;
import com.market.jdbc.DBUtil;
import java.sql.*;

public class BookInIt {
    private static ArrayList<Book> mBookList;
    private static int mTotalBook = 0;

    public static void init() {
        mTotalBook = totalFileToBookList();
        mBookList = new ArrayList<Book>();
        setFileToBookList(mBookList);
    }

    public static int totalFileToBookList() {
        try {
            FileReader fr = new FileReader("book.txt");
            BufferedReader reader = new BufferedReader(fr);

            String str;
            int num = 0;
            while ((str = reader.readLine()) != null) {
                if (str.contains("ISBN"))
                    ++num;
            }
            reader.close();
            fr.close();
            return num;
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static void setFileToBookList(ArrayList<Book> booklist) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM books";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                String publisher = rs.getString("publisher");
                String category = rs.getString("category");
                String releaseDate = rs.getString("release_date");

                // Book 생성자: (isbn, title, author, price, stock, publisher, category, releaseDate)
                Book book = new Book(isbn, title, author, price, stock, publisher, category, releaseDate);
                booklist.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Book> getmBookList() {
        return mBookList;
    }

    public static void setmBookList(ArrayList<Book> mBookList) {
        BookInIt.mBookList = mBookList;
    }

    public static int getmTotalBook() {
        return mTotalBook;
    }

    public static void setmTotalBook(int mTotalBook) {
        BookInIt.mTotalBook = mTotalBook;
    }
}
