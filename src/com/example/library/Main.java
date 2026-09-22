package com.example.library;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library(10, 10, 10);
        lib.addBook(new Book("B001", "9787111547426", "算法导论", "CLRS"));
        lib.addUser(new Reader("R001", "张三"));
        lib.addUser(new Reader("R002", "李四"));

        System.out.println(lib.borrowBook("B001", "R001"));   // 预测：？
        System.out.println(lib.findBookById("B001"));         // 预测：available=？
        System.out.println(lib.getLoanCount());               // 预测：？
        System.out.println(lib.borrowBook("B001", "R002"));   // 预测：？
        lib.borrowBook("B002", "R001");                       // 预测：？
        lib.borrowBook("B001", "R999");                       // 预测：？                      // 抛 UserNotFoundException
    }
}
