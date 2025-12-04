package com.market.page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.market.cart.Cart;
import com.market.member.UserInIt;
import com.market.member.User;

public class CartShippingPage extends JPanel {

    Cart mCart;
    JPanel shippingPanel;
    JPanel radioPanel;
    private JTextField addressText;
    private JTextField betterAddressText;

    public CartShippingPage(JPanel panel, Cart cart) {

        Font ft = new Font("함초롬돋움", Font.BOLD, 15);
        setLayout(null);

        Rectangle rect = panel.getBounds();
        setPreferredSize(rect.getSize());

        radioPanel = new JPanel();
        radioPanel.setBounds(300, 0, 700, 50);
        radioPanel.setLayout(new FlowLayout());
        add(radioPanel);

        JLabel radioLabel = new JLabel("배송받을 분은 고객정보와 같습니까?");
        radioLabel.setFont(ft);
        JRadioButton radioOk = new JRadioButton("예");
        radioOk.setFont(ft);
        JRadioButton radioNo = new JRadioButton("아니오");
        radioNo.setFont(ft);
        radioPanel.add(radioLabel);
        radioPanel.add(radioOk);
        radioPanel.add(radioNo);

        shippingPanel = new JPanel();
        shippingPanel.setBounds(200, 50, 700, 500);
        shippingPanel.setLayout(null);
        add(shippingPanel);

        radioOk.setSelected(true);
        radioNo.setSelected(false);
        this.mCart = cart;

        UserShippingInfo(true);

        radioOk.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (radioOk.isSelected()) {
                    shippingPanel.removeAll();
                    UserShippingInfo(true);
                    shippingPanel.revalidate();
                    shippingPanel.repaint();
                    radioNo.setSelected(false);
                }
            }
        });

        radioNo.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (radioNo.isSelected()) {
                    shippingPanel.removeAll();
                    UserShippingInfo(false);
                    shippingPanel.revalidate();
                    shippingPanel.repaint();
                    radioOk.setSelected(false);
                }
            }
        });
    }

    public void UserShippingInfo(boolean select) {
        Font ft = new Font("함초롬돋움", Font.BOLD, 15);

        User user = UserInIt.getmUser();

        JPanel namePanel = new JPanel();
        namePanel.setBounds(0, 100, 700, 50);
        JLabel nameLabel = new JLabel("고객명 : ");
        nameLabel.setFont(ft);
        namePanel.add(nameLabel);

        JTextField nameLabel2 = new JTextField(15);
        nameLabel2.setFont(ft);
        if (select && user != null) {
            nameLabel2.setBackground(Color.LIGHT_GRAY);
            nameLabel2.setText(user.getName());
        }
        namePanel.add(nameLabel2);
        shippingPanel.add(namePanel);

        JPanel phonePanel = new JPanel();
        phonePanel.setBounds(0, 150, 700, 50);
        JLabel phoneLabel = new JLabel("연락처 : ");
        phoneLabel.setFont(ft);
        phonePanel.add(phoneLabel);

        JTextField phoneLabel2 = new JTextField(15);
        phoneLabel2.setFont(ft);
        if (select && user != null) {
            phoneLabel2.setBackground(Color.LIGHT_GRAY);
            phoneLabel2.setText(user.getPhone());
        }
        phonePanel.add(phoneLabel2);
        shippingPanel.add(phonePanel);

        JPanel addressPanel = new JPanel();
        addressPanel.setBounds(0, 200, 700, 50);
        JLabel label = new JLabel("주소 : ");
        label.setFont(ft);
        addressPanel.add(label);

        addressText = new JTextField(15);
        addressText.setFont(ft);
        if (select && user != null) {
        	addressText.setBackground(Color.LIGHT_GRAY);
            addressText.setText(user.getAddress());
        }
        addressPanel.add(addressText);
        shippingPanel.add(addressPanel);

        JPanel betterPanel = new JPanel();
        betterPanel.setBounds(0, 250, 700, 50);
        JLabel betterLabel = new JLabel("상세주소 : ");
        betterLabel.setFont(ft);
        betterPanel.add(betterLabel);

        betterAddressText = new JTextField(15);
        betterAddressText.setFont(ft);
        if (select && user != null) {
        	addressText.setBackground(Color.LIGHT_GRAY);
            betterAddressText.setText(user.getBetterAddress());
        }
        betterPanel.add(betterAddressText);
        shippingPanel.add(betterPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 320, 700, 100);

        JLabel buttonLabel = new JLabel("주문하기");
        buttonLabel.setFont(new Font("함초롬돋움", Font.BOLD, 15));
        JButton orderButton = new JButton();
        orderButton.add(buttonLabel);
        buttonPanel.add(orderButton);
        shippingPanel.add(buttonPanel);

        orderButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Component parent = SwingUtilities.getWindowAncestor((Component) e.getSource());
                new MockPaymentDialog((JFrame) parent).setVisible(true);

                String addr = addressText.getText();
                String better = betterAddressText.getText();

                System.out.println("📦 배송지: " + addr);
                System.out.println("📦 상세주소: " + better);

                radioPanel.removeAll();
                radioPanel.revalidate();
                radioPanel.repaint();

                shippingPanel.removeAll();
                shippingPanel.add("주문 배송지", new CartOrderBillPage(shippingPanel, mCart, addr));

                mCart.deleteBook();
                shippingPanel.revalidate();
                shippingPanel.repaint();
            }
        });
    }
}
