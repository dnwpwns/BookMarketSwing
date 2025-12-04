package com.market.member;

public class User extends Person {
    private String email;
    private String password;
    private String regDate;
    private String betterAddress;
    private int purchase;
    private int totalSpent;

    public User(String email, String password, String name, String phone, String regDate,
                String address, String betterAddress, int purchase, int totalSpent) {
        super(name, phone, address);
        this.email = email;
        this.password = password;
        this.regDate = regDate;
        this.betterAddress = betterAddress;
        this.purchase = purchase;
        this.totalSpent = totalSpent;
    }

    // Getter 추가
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRegDate() { return regDate; }
    public String getBetterAddress() { return betterAddress; }
    public int getPurchase() { return purchase; }
    public int getTotalSpent() { return totalSpent; }
}
