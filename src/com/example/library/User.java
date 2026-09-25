package com.example.library;

public abstract class User {
    //抽象类,内部三个字段id         id,String final
    //               name       名称,String final
    //            borrowCount   该用户正在借阅的书籍数,int
    //             loanLimit    同时借阅限额,final int
    //默认定义
    //       getId       获取Id
    //       getName     获取名称

    private final String id;
    private final String name;
    private int borrowCount;
    private final int loanLimit;


    public User(String id, String name, int loanLimit) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("invalid id!");
        }
        else if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("invalid name!");
        }
        else if (loanLimit <= 0) {          // ← 还没加
            throw new IllegalArgumentException("loanLimit must be positive");
        }
        else {
            this.id = id;
            this.name = name;
            borrowCount = 0;
            this.loanLimit = loanLimit;
        }
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }

    public int getLoanLimit() {
        return loanLimit;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String getRoleName();

    @Override
    public String toString() {
        String s = getRoleName() + "{id='" + id + "', name='" + name + "'}";
        return s;
    }
}
