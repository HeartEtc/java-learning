package com.example.test;

class BasicAccount extends Account {

    public BasicAccount(String id, String owner, double balance) {
        super(id, owner, balance);
    }

    @Override
    public void endOfMonth() {
        // 普通账户没有月末动作，空实现
    }
}