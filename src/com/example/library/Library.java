package com.example.library;

public class Library {
    //图书的存储,新建指定长度的数组,数组具体内容见Book
    /*
    额外定义     addBook        加书
               deleteBook     删除书
               addReader      添加用户
               deleteReader   删除用户
               borrowBook         借阅
               returnBook         还书
     */

    private final User[] users;
    private final Book[] books;
    private final Loan[] loans;
    private int bookCount;
    private int userCount;
    private int loanCount;

    public Library(int userCapacity, int bookCapacity, int loanCapacity) {
        this.users = new User[userCapacity];
        this.books = new Book[bookCapacity];
        this.loans = new Loan[loanCapacity];
        bookCount = 0;
        userCount = 0;
        loanCount = 0;
    }


}
