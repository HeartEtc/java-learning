package com.example.test;

public class BankDemo {
    public static void main(String[] args) {
        Bank bank = new Bank(5);

        System.out.println("=== 1. 开户 ===");
        System.out.println("开户成功: " + bank.open(new BasicAccount("A001", "张三", 1000.0)));
        System.out.println("开户成功: " + bank.open(new SavingsAccount("S001", "李四", 2000.0, 0.03)));
        System.out.println("账户数: " + bank.getAccountCount());
        System.out.println("总资产: " + bank.getTotalBalance());

        System.out.println("\n=== 2. 存取 ===");
        Account zhangsan = bank.findById("A001");
        System.out.println("deposit(500): " + zhangsan.deposit(500));
        System.out.println("withdraw(200): " + zhangsan.withdraw(200));
        System.out.println("张三余额: " + zhangsan.getBalance());

        System.out.println("\n=== 3. 月末结算 ===");
        bank.endOfMonthForAll();
        System.out.println(bank.findById("A001"));
        System.out.println(bank.findById("S001"));
        System.out.println("总资产: " + bank.getTotalBalance());

        System.out.println("\n=== 4. 转账 ===");
        System.out.println("转账成功: " + zhangsan.transferTo(bank.findById("S001"), 300.0));
        System.out.println("张三余额: " + zhangsan.getBalance());
        System.out.println("总资产: " + bank.getTotalBalance());

        System.out.println("\n=== 5. 边界 ===");
        System.out.println("查不到: " + bank.findById("X999"));
        System.out.println(bank.findById("X999").getBalance());
    }
}