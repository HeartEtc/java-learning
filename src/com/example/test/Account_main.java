package com.example.test;

public class Account_main {
    public static void main(String[] args) {
        Account[] accounts = new Account[3];
        accounts[0] = new Account("A001", "张三", 1000.0);
        accounts[1] = new SavingsAccount("S001", "李四", 2000.0, 0.03);
        accounts[2] = new SavingsAccount("S002", "王五", 500.0, 0.05);

        for (int i = 0; i < accounts.length; i++) {
            System.out.println(accounts[i]);
        }
    }
}

class Account{
    private final String id;
    private final String owner;
    private double balance;

    public Account(String id, String owner, double balance){
        if(id == null || id.isBlank()){
            throw new IllegalArgumentException("invalid id!");
        }
        else if(owner == null || owner.isBlank()){
            throw new IllegalArgumentException("invalid owner!");
        }
        else if(balance < 0){
            throw new IllegalArgumentException("balance can NOT be less than 0!");
        }
        else {
            this.id = id;
            this.owner = owner;
            this.balance = balance;
        }
    }

    public boolean deposit(double amount){
        if (amount <= 0){
            return false;
        }
        else{
            balance += amount;
            return true;
        }
    }

    public boolean withdraw(double amount){
        if (amount <= 0 || amount > balance){
            return false;
        }
        else{
            balance -= amount;
            return true;
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getOwner(){
        return owner;
    }

    public String getId(){
        return id;
    }

    public String toString(){
        return "Account{id='" + id + "', owner='" + owner + "', balance=" + balance + "}";
    }
}

class SavingsAccount extends Account{
    private final double rate;

    public SavingsAccount(String id, String owner, double balance,double rate) {
        super(id, owner, balance);
        if(0 <= rate && rate < 1){
            this.rate = rate;
        }
        else{
            throw new IllegalArgumentException("invalid rate!");
        }
    }

    public void addInterest(){
        double result = getBalance() * rate;
        if (result > 0){
        deposit(result);
        }
    }

    public double getRate(){
        return rate;
    }

    @Override
    public String toString(){
        return "SavingsAccount{id='" + getId() + "', owner='" + getOwner()
                + "', balance=" + getBalance() + ", rate=" + rate +"}";
    }
}

public static void addInterestToAll(Account[] accounts) {
    for (int i = 0; i < accounts.length; i++) {
        if (accounts[i] instanceof SavingsAccount sa){
            sa.addInterest();
        }
    }
}
