package com.example.library;

/**
 * P1 图书管理系统的自测入口。
 *
 * 约定：**每一段测试都新建一个 Library 实例**。
 * 复用同一个实例会让前一段的状态污染后一段，从而出现"看起来像 bug 其实是用例问题"的假象。
 */
public class Main {
    public static void main(String[] args) {

        // ============================================================
        // A. 借出后不可再借
        // ============================================================
        System.out.println("=== A. 借出后不可再借 ===");
        Library a = new Library(5, 5, 5);
        a.addUser(new Reader("R001", "张三", 3));
        a.addUser(new Reader("R002", "李四", 3));
        a.addBook(new Book("B001", "9787111547426", "算法导论", "CLRS"));

        System.out.println("张三借B001(期望true): " + a.borrowBook("B001", "R001"));
        System.out.println("借出后available(期望false): " + a.findBookById("B001").isAvailable());
        System.out.println("李四再借B001(期望false): " + a.borrowBook("B001", "R002"));
        System.out.println("借阅记录数(期望1): " + a.getLoanCount());

        // ============================================================
        // B. 同一用户借两本，只还其中一本
        // ============================================================
        System.out.println("\n=== B. 同一用户借两本，只还一本 ===");
        Library b = new Library(5, 5, 5);
        Reader r1 = new Reader("R001", "张三", 3);
        b.addUser(r1);
        b.addBook(new Book("B001", "9787111547426", "算法导论", "CLRS"));
        b.addBook(new Book("B002", "9787111213826", "Java编程思想", "Eckel"));
        b.borrowBook("B001", "R001");
        b.borrowBook("B002", "R001");

        System.out.println("借阅数(期望2): " + b.getLoanCount());
        System.out.println("还B002(期望true): " + b.returnBook("B002", "R001"));
        System.out.println("借阅数(期望1): " + b.getLoanCount());
        System.out.println("B001仍在借(期望false): " + b.findBookById("B001").isAvailable());
        System.out.println("B002已归还(期望true): " + b.findBookById("B002").isAvailable());
        System.out.println("张三在借数(期望1): " + r1.getBorrowCount());

        // ============================================================
        // C. 替别人还书 —— 匹配条件必须是"书 + 借阅人"同时匹配
        // ============================================================
        System.out.println("\n=== C. 替别人还书 ===");
        Library c = new Library(5, 5, 5);
        c.addUser(new Reader("R001", "张三", 3));
        c.addUser(new Reader("R002", "李四", 3));
        c.addBook(new Book("B001", "9787111547426", "算法导论", "CLRS"));
        c.borrowBook("B001", "R002");

        System.out.println("张三还李四的书(期望false): " + c.returnBook("B001", "R001"));
        System.out.println("借阅数(期望1，未被误删): " + c.getLoanCount());
        System.out.println("书仍在借(期望false): " + c.findBookById("B001").isAvailable());

        // ============================================================
        // D. deleteUser 的"还有书没还"判断
        // ============================================================
        System.out.println("\n=== D. 删除用户的状态判断 ===");
        Library d = new Library(5, 5, 5);
        Admin admin = new Admin("M001", "王老师", 5);
        Reader r2 = new Reader("R002", "李四", 3);
        d.addUser(admin);
        d.addUser(r2);
        d.addBook(new Book("B001", "9787111547426", "算法导论", "CLRS"));
        d.borrowBook("B001", "R002");

        System.out.println("李四在借数(期望1): " + r2.getBorrowCount());
        System.out.println("删还有书的用户(期望false): " + d.deleteUser(admin, "R002"));
        System.out.println("用户数(期望2): " + d.getUserCount());
        d.returnBook("B001", "R002");
        System.out.println("还清后在借数(期望0): " + r2.getBorrowCount());
        System.out.println("删已还清的用户(期望true): " + d.deleteUser(admin, "R002"));
        System.out.println("用户数(期望1): " + d.getUserCount());

        // ============================================================
        // E. 权限：读者不能删书 / 在借的书不能删
        // ============================================================
        System.out.println("\n=== E. 删除书籍的权限与状态判断 ===");
        Library e = new Library(5, 5, 5);
        Admin adminE = new Admin("M001", "王老师", 5);
        Reader r4 = new Reader("R001", "张三", 3);
        e.addUser(adminE);
        e.addUser(r4);
        e.addBook(new Book("B001", "9787111547426", "算法导论", "CLRS"));
        e.addBook(new Book("B002", "9787111213826", "Java编程思想", "Eckel"));

        System.out.println("读者删书(期望false): " + e.deleteBook(r4, "B001"));
        System.out.println("书数(期望2): " + e.getBookCount());
        e.borrowBook("B002", "R001");
        System.out.println("删在借的书(期望false): " + e.deleteBook(adminE, "B002"));
        System.out.println("管理员删可借的书(期望true): " + e.deleteBook(adminE, "B001"));
        System.out.println("书数(期望1): " + e.getBookCount());

        // ============================================================
        // F. loanLimit：同时在借上限，归还后额度释放
        // ============================================================
        System.out.println("\n=== F. 同时在借上限 ===");
        Library f = new Library(5, 5, 5);
        Reader r3 = new Reader("R003", "王五", 2);
        f.addUser(r3);
        f.addBook(new Book("B001", "9787111547426", "算法导论", "CLRS"));
        f.addBook(new Book("B002", "9787111213826", "Java编程思想", "Eckel"));
        f.addBook(new Book("B003", "9787111213827", "CSAPP", "Bryant"));

        System.out.println("借1(期望true): " + f.borrowBook("B001", "R003"));
        System.out.println("借2(期望true): " + f.borrowBook("B002", "R003"));
        System.out.println("借3超上限(期望false): " + f.borrowBook("B003", "R003"));
        f.returnBook("B001", "R003");
        System.out.println("还1本后在借数(期望1): " + r3.getBorrowCount());
        System.out.println("再借1本(期望true): " + f.borrowBook("B003", "R003"));

        // ============================================================
        // G. 异常：找不到书 / 找不到用户
        // ============================================================
        System.out.println("\n=== G. 异常 ===");
        try {
            a.borrowBook("X999", "R001");
        }
        catch (com.example.library.exception.BookNotFoundException ex) {
            System.out.println("捕获到: " + ex.getMessage());
        }
        System.out.println("异常被捕获后程序继续执行");
    }
}
