package com.example.library;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library(10, 10, 10);
        System.out.println(lib.addBook(new Book("B001", "9787111547426", "算法导论", "CLRS")));   // true
        System.out.println(lib.addBook(new Book("B002", "9787111213826", "Java编程思想", "Eckel"))); // true
        System.out.println(lib.getBookCount());                       // 2
        System.out.println(lib.addUser(new Reader("R001", "张三")));   // true
        System.out.println(lib.getUserCount());                       // 1
        System.out.println(lib.findBookById("B001"));                 // Book{...}
        System.out.println(lib.findUserById("R001"));                 // 读者{...}
        lib.findBookById("X999");
    }
}
