package com.example.test;

public class Bank {
    private final Account[] accounts;
    private int count;

    public Bank(int capacity) {
        this.accounts = new Account[capacity];
        count = 0;
    }

    public boolean open(Account account) {
        if (count >= accounts.length) {     // 直接用 count 判断满没满
            return false;
        }
        accounts[count] = account;
        count++;
        return true;
    }

    public Account findById(String id) {
        for (int i = 0; i < count; i++) {
            if (id.equals(accounts[i].getId())) {
                return accounts[i];
            }
        }
        return null;
    }

    public double getTotalBalance() {
        double totalbalance = 0;
        for (int i = 0; i < count; i++) {
            totalbalance += accounts[i].getBalance();
        }
        return totalbalance;
    }

    public void endOfMonthForAll() {
        for (int i = 0; i < count; i++) {
            accounts[i].endOfMonth();
        }
    }

    public int getAccountCount(){
        return count;
    }
}
