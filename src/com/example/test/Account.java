package com.example.test;

abstract class Account {
    private final String id;
    private final String owner;
    private double balance;

    public Account(String id, String owner, double balance) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("invalid id!");
        } else if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("invalid owner!");
        } else if (balance < 0) {
            throw new IllegalArgumentException("balance can NOT be less than 0!");
        } else {
            this.id = id;
            this.owner = owner;
            this.balance = balance;
        }
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        } else {
            balance += amount;
            return true;
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        } else {
            balance -= amount;
            return true;
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return "Account{id='" + id + "', owner='" + owner + "', balance=" + balance + "}";
    }

    public abstract void endOfMonth();

    public boolean transferTo(Account target, double amount) {
        if (target == null || target == this || amount <= 0) {
            return false;
        }
        if (!withdraw(amount)) {
            return false;
        }
        if (!target.deposit(amount)) {
            deposit(amount);          // 回滚
            return false;
        }
        return true;
    }
}
