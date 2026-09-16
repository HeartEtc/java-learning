package com.example.test;

class SavingsAccount extends Account {
    private final double rate;

    public SavingsAccount(String id, String owner, double balance, double rate) {
        super(id, owner, balance);
        if (0 <= rate && rate < 1) {
            this.rate = rate;
        } else {
            throw new IllegalArgumentException("invalid rate!");
        }
    }

    public void addInterest() {
        double result = getBalance() * rate;
        if (result > 0) {
            deposit(result);
        }
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "SavingsAccount{id='" + getId() + "', owner='" + getOwner()
                + "', balance=" + getBalance() + ", rate=" + rate + "}";
    }
}
