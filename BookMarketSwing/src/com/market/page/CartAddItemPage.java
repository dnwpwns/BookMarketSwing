package com.market.page;

import javax.swing.*;
import com.market.bookitem.Book;
import com.market.bookitem.BookInIt;
import com.market.cart.Cart;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.net.URL;
import javax.swing.table.DefaultTableCellRenderer;

public class CartAddItemPage extends JPanel {
	private JComboBox<String> categoryCombo;
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    private JLabel imageLabel;
    private JTable bookTable;
    private JLabel pageLabel;

    private int mSelectRow = 0;
    private int currentPage = 0;
    private final int itemsPerPage = 20;
    private ArrayList<Book> booklist;
    private final Cart mCart;

    public CartAddItemPage(JPanel panel, Cart cart) {
        setLayout(null);
        setPreferredSize(panel.getBounds().getSize());

        mCart = cart;
        booklist = BookInIt.getmBookList();

        // 이미지 영역
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);
        imagePanel.setBounds(20, 0, 300, 500);

        imageLabel = new JLabel("이미지 미리보기", SwingConstants.CENTER);
        imageLabel.setBounds(25, 10, 250, 300);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePanel.add(imageLabel);

        String[] categories = { "전체", "자바", "파이썬", "c", "c++", "웹 개발", "웹 프레임워크", "데이터베이스", "인공지능", "자료구조", "기타" };
        categoryCombo = new JComboBox<>(categories);
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        categoryCombo.setBounds(25, 380, 250, 30);
        categoryCombo.addActionListener(e -> {
            String selectedCategory = (String) categoryCombo.getSelectedItem();
            filterByCategory(selectedCategory);
        });
        imagePanel.add(categoryCombo);

        // 검색 영역
        searchTypeCombo = new JComboBox<>(new String[] { "도서ID", "도서명" });
        searchTypeCombo.setBounds(25, 430, 100, 25);
        imagePanel.add(searchTypeCombo);

        searchField = new JTextField();
        searchField.setBounds(130, 430, 95, 25);
        imagePanel.add(searchField);

        JButton searchBtn = new JButton("검색");
        searchBtn.setBounds(230, 430, 70, 25);
        searchBtn.addActionListener(e -> {
            String type = (String) searchTypeCombo.getSelectedItem();
            String keyword = searchField.getText().trim();
            searchByKeyword(type, keyword);
        });
        imagePanel.add(searchBtn);

        add(imagePanel);

        // 도서 목록 테이블
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(320, 0, 680, 400);
        add(tablePanel);

        bookTable = new JTable();
        JScrollPane jScrollPane = new JScrollPane(bookTable);
        jScrollPane.setPreferredSize(new Dimension(650, 345));
        tablePanel.add(jScrollPane);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        bookTable.setRowHeight(16);

        updateTable();

        bookTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = bookTable.getSelectedRow();
                mSelectRow = row;

                String imageUrl = (String) bookTable.getValueAt(row, 5);
                try {
                    URL url = new URL(imageUrl);
                    ImageIcon icon = new ImageIcon(url);
                    Image scaled = icon.getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaled));
                    imageLabel.setText("");
                } catch (Exception ex) {
                    imageLabel.setIcon(null);
                    imageLabel.setText("이미지 불러오기 실패");
                }
            }
        });

        // 하단 버튼 영역
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBounds(0, 400, 1000, 100);
        add(buttonPanel);

        JPanel pagePanel = new JPanel();
        JButton prevBtn = new JButton("◀ 이전");
        JButton nextBtn = new JButton("다음 ▶");
        pageLabel = new JLabel("Page: 1");

        prevBtn.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateTable();
                pageLabel.setText("Page: " + (currentPage + 1));
            }
        });

        nextBtn.addActionListener(e -> {
            if ((currentPage + 1) * itemsPerPage < booklist.size()) {
                currentPage++;
                updateTable();
                pageLabel.setText("Page: " + (currentPage + 1));
            }
        });

        pagePanel.add(prevBtn);
        pagePanel.add(pageLabel);
        pagePanel.add(nextBtn);

        JPanel cartButtonPanel = new JPanel();
        JButton addButton = new JButton("장바구니에 담기");

        addButton.addActionListener(e -> {
            int modelRow = mSelectRow;
            int realIndex = currentPage * itemsPerPage + modelRow;
            if (realIndex >= 0 && realIndex < booklist.size()) {
                Book selectedBook = booklist.get(realIndex);
                int select = JOptionPane.showConfirmDialog(addButton, "장바구니에 추가하겠습니까?");
                if (select == 0) {
                    if (!isCartInBook(selectedBook.getBookId())) {
                        mCart.insertBook(selectedBook);
                    }
                    JOptionPane.showMessageDialog(addButton, "추가했습니다");
                }
            }
        });
        cartButtonPanel.add(addButton);

        buttonPanel.add(pagePanel, BorderLayout.NORTH);
        buttonPanel.add(cartButtonPanel, BorderLayout.SOUTH);
    }

    private void updateTable() {
        int start = currentPage * itemsPerPage;
        int end = Math.min(start + itemsPerPage, booklist.size());
        String[] headers = { "No", "도서ID", "도서명", "가격", "저자", "설명", "분야", "출판일" };
        Object[][] data = new Object[end - start][8];

        for (int i = start; i < end; i++) {
            Book book = booklist.get(i);
            int row = i - start;
            data[row][0] = i + 1;
            data[row][1] = book.getBookId();
            data[row][2] = book.getName();
            data[row][3] = book.getUnitPrice();
            data[row][4] = book.getAuthor();
            data[row][5] = book.getDescription();
            data[row][6] = book.getCategory();
            data[row][7] = book.getReleaseDate();
        }

        bookTable.setModel(new javax.swing.table.DefaultTableModel(data, headers));
    }

    public boolean isCartInBook(String bookId) {
        return mCart.isCartInBook(bookId);
    }

    private void filterByCategory(String selectedCategory) {
        ArrayList<Book> allBooks = BookInIt.getmBookList();
        if (selectedCategory.equals("전체")) {
            booklist = allBooks;
        } else {
            booklist = new ArrayList<>();
            for (Book book : allBooks) {
                if (book.getCategory().equalsIgnoreCase(selectedCategory)) {
                    booklist.add(book);
                }
            }
        }
        currentPage = 0;
        updateTable();
        pageLabel.setText("Page: 1");
    }

    private void searchByKeyword(String type, String keyword) {
        ArrayList<Book> filteredBase = new ArrayList<>();

        // 👉 현재 선택된 카테고리 얻기
        String selectedCategory = (String) categoryCombo.getSelectedItem();

        // 👉 필터 대상 리스트 구성
        if (selectedCategory.equals("전체")) {
            filteredBase = BookInIt.getmBookList();
        } else {
            for (Book book : BookInIt.getmBookList()) {
                if (book.getCategory().equalsIgnoreCase(selectedCategory)) {
                    filteredBase.add(book);
                }
            }
        }

        // 👉 필터링된 카테고리 내에서만 검색 실행
        booklist = new ArrayList<>();
        for (Book book : filteredBase) {
            if (type.equals("도서ID") && book.getBookId().contains(keyword)) {
                booklist.add(book);
            } else if (type.equals("도서명") && book.getName().contains(keyword)) {
                booklist.add(book);
            }
        }

        currentPage = 0;
        updateTable();
        pageLabel.setText("Page: 1");
    }
}
