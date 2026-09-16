package com.example.test;

public class Account_main {
    public static void main(String[] args) {
        Account[] accounts = new Account[3];
        accounts[0] = new Account("A001", "张三", 1000.0);
        accounts[1] = new SavingsAccount("S001", "李四", 2000.0, 0.03);
        accounts[2] = new SavingsAccount("S002", "王五", 500.0, 0.05);

        System.out.println("--- 计息前 ---");
        for (int i = 0; i < accounts.length; i++) {
            System.out.println(accounts[i]);
        }

        addInterestToAll(accounts);          // ← 缺的就是这一行

        System.out.println("--- 计息后 ---");
        for (int i = 0; i < accounts.length; i++) {
            System.out.println(accounts[i]);
        }
    }

    public static void addInterestToAll(Account[] accounts) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] instanceof SavingsAccount sa){
                sa.addInterest();
            }
        }
    }
}


