package com.example.library;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library(10, 10, 10);
        lib.addBook(new Book("B001", "9787111547426", "算法导论", "CLRS"));
        lib.addUser(new Reader("R001", "张三"));
        lib.addUser(new Reader("R002", "李四"));

        lib.borrowBook("B001", "R001");
        System.out.println(lib.getLoanCount());       // 1
        System.out.println(lib.findBookById("B001")); // available=false
        System.out.println(lib.returnBook("B001", "R001"));   // true
        System.out.println(lib.getLoanCount());       // 0
        System.out.println(lib.findBookById("B001")); // available=true ← 关键
        System.out.println(lib.returnBook("B001", "R001"));   // false（已经还过了）
        System.out.println(lib.borrowBook("B001", "R002"));   // true ← 证明容量循环使用
        System.out.println("============================================");
        // 加 3 本书，但只借 1 本 → bookCount=3, loanCount=1
        lib.addBook(new Book("B002", "9787111213826", "Java编程思想", "Eckel"));
        lib.addBook(new Book("B003", "9787111213827", "CSAPP", "Bryant"));
        lib.borrowBook("B001", "R001");
        lib.returnBook("B001", "R001");      // 旧代码在这里就崩了
        System.out.println("============================================");
        // 借阅记录容量只给 2
        Library small = new Library(5, 5, 2);
        small.addBook(new Book("B001", "9787111547426", "算法导论", "CLRS"));
        small.addBook(new Book("B002", "9787111213826", "Java编程思想", "Eckel"));
        small.addUser(new Reader("R001", "张三"));

        small.borrowBook("B001", "R001");     // loanCount = 1
        small.borrowBook("B002", "R001");     // loanCount = 2 ← 数组满了
        System.out.println(small.getLoanCount());          // 2

        small.returnBook("B001", "R001");     // ← 旧代码在这里抛 ArrayIndexOutOfBoundsException
        System.out.println(small.getLoanCount());          // 期望 1
        System.out.println(small.findBookById("B001"));    // available=true
        System.out.println(small.findBookById("B002"));    // available=false（还没还）
    }
}
