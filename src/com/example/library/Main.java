package com.example.library;

import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.UserNotFoundException;

import java.util.Scanner;

/**
 * P1 图书管理系统的界面。
 *
 * 结构约定（从高层到低层）：
 *   main            主菜单 + 登录分发
 *   runReaderSession / runAdminSession   一次登录会话的内部循环
 *   addBook / deleteBook / deleteUser    管理员的单个操作
 *   login / printLoans / readLine / readChoice   工具方法
 *
 * 注销的实现方式：会话方法用 while(true) + return —— 返回即注销。
 * 异常边界：try 只包住"单次操作"的 switch，因此操作失败不会中断会话。
 */
public class Main {

    public static void main(String[] args) {
        Library library = SampleData.createLibrary();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("图书馆系统\n功能列表:\n1:注册读者\n2:查看书籍\n3:登录读者\n0:退出");
            int choice = readChoice(sc, 3);
            if (choice == 0) {
                break;
            }

            try {
                switch (choice) {
                    case 1 -> register(library, sc);
                    case 2 -> library.printAllBooks();
                    case 3 -> login(library, sc);
                }
            }
            catch (UserNotFoundException | BookNotFoundException e) {
                System.out.println("操作失败: " + e.getMessage());
            }
        }

        System.out.println("已退出，再见");
    }

    // ==================== 会话 ====================

    /** 登录并进入对应会话。 */
    private static void login(Library library, Scanner sc) {
        System.out.println("读者登录\n请输入id");
        String userid = readLine(sc);
        System.out.println("请输入用户名:");
        String username = readLine(sc);

        User user = library.findUserById(userid);
        if (!user.getName().equals(username)) {
            System.out.println("登录失败");
            return;
        }

        System.out.println("登录成功,欢迎," + username);
        if (user instanceof Admin) {
            runAdminSession(library, sc, userid, username);
        }
        else {
            runReaderSession(library, sc, userid, username);
        }
    }

    /** 读者会话。返回即注销。 */
    private static void runReaderSession(Library library, Scanner sc, String userid, String username) {
        while (true) {
            System.out.println("图书馆系统     当前登录:" + username
                    + "\n功能列表:\n1:查看书籍\n2:借阅书籍\n3:归还书籍\n4:我的借阅"
                    + "\n0:注销");
            int choice = readChoice(sc, 4);
            if (choice == 0) {
                return;
            }

            try {
                switch (choice) {
                    case 1 -> library.printAllBooks();
                    case 2 -> borrowBook(library, sc, userid);
                    case 3 -> returnBook(library, sc);
                    case 4 -> printLoans(library.getActiveUserLoans(userid));
                }
            }
            catch (UserNotFoundException | BookNotFoundException e) {
                System.out.println("操作失败: " + e.getMessage());
            }
        }
    }

    /** 管理员会话。返回即注销。 */
    private static void runAdminSession(Library library, Scanner sc, String userid, String username) {
        while (true) {
            System.out.println("图书馆系统     当前登录(管理员权限):" + username
                    + "\n功能列表:\n1:查看书籍\n2:借阅书籍\n3:归还书籍\n4:我的借阅"
                    + "\n5:添加书籍\n6:删除书籍\n7:查找用户\n8:删除用户\n0:注销");
            int choice = readChoice(sc, 8);
            if (choice == 0) {
                return;
            }

            try {
                switch (choice) {
                    case 1 -> library.printAllBooks();
                    case 2 -> borrowBook(library, sc, userid);
                    case 3 -> returnBook(library, sc);
                    case 4 -> printLoans(library.getActiveUserLoans(userid));
                    case 5 -> addBook(library, sc);
                    case 6 -> deleteBook(library, sc);
                    case 7 -> findUser(library, sc);
                    case 8 -> deleteUser(library, sc);
                }
            }
            catch (UserNotFoundException | BookNotFoundException e) {
                System.out.println("操作失败: " + e.getMessage());
            }
        }
    }

    // ==================== 单个操作 ====================

    private static void register(Library library, Scanner sc) {
        System.out.println("读者注册\n请输入id:");
        String userid = readLine(sc);
        System.out.println("请输入用户名:");
        String username = readLine(sc);

        if (library.addUser(new Reader(userid, username, 3))) {
            System.out.println("注册成功");
        }
        else {
            System.out.println("注册失败：用户已满");
        }
    }

    private static void borrowBook(Library library, Scanner sc, String userid) {
        System.out.println("借阅书籍\n请输入书籍id:");
        String bookId = readLine(sc);

        if (library.borrowBook(bookId, userid)) {
            System.out.println("借阅成功!默认还书期限为30天!");
        }
        else {
            System.out.println("借阅失败!");
        }
    }

    private static void returnBook(Library library, Scanner sc) {
        System.out.println("归还书籍\n请输入书籍id:");
        String bookId = readLine(sc);
        System.out.println("请输入归还用户id:");
        String userId = readLine(sc);

        if (library.returnBook(bookId, userId)) {
            System.out.println("归还成功!欢迎下次借阅!");
        }
        else {
            System.out.println("归还失败!");
        }
    }

    private static void addBook(Library library, Scanner sc) {
        System.out.println("添加书籍\n请输入书籍id:");
        String newbookid = readLine(sc);
        System.out.println("请输入书籍isbn码:");
        String newbookisbn = readLine(sc);
        System.out.println("请输入书籍名称:");
        String newbookname = readLine(sc);
        System.out.println("请输入书籍作者:");
        String newbookauthor = readLine(sc);

        if (library.addBook(new Book(newbookid, newbookisbn, newbookname, newbookauthor))) {
            System.out.println("添加成功!");
        }
        else {
            System.out.println("添加失败!");
        }
    }

    private static void deleteBook(Library library, Scanner sc) {
        System.out.println("删除书籍\n权限核验:\n请输入您的用户id:");
        String operatorId = readLine(sc);
        User operator = library.findUserById(operatorId);      // 先查，id 不存在会抛异常
        System.out.println("请输入书籍id:");
        String bookId = readLine(sc);

        if (library.deleteBook(operator, bookId)) {
            System.out.println("删除成功!");
        }
        else {
            System.out.println("删除失败!");
        }
    }

    private static void findUser(Library library, Scanner sc) {
        System.out.println("查询用户\n请输入目标用户id:");
        String finduserid = readLine(sc);
        System.out.println(library.findUserById(finduserid));
    }

    private static void deleteUser(Library library, Scanner sc) {
        System.out.println("删除用户(谨慎操作!)\n权限核验:\n请输入您的用户id:");
        String operatorId = readLine(sc);
        User operator = library.findUserById(operatorId);      // 先查，id 不存在会抛异常
        System.out.println("请输入删除目标用户id:");
        String targetId = readLine(sc);

        if (library.deleteUser(operator, targetId)) {
            System.out.println("删除成功!");
        }
        else {
            System.out.println("删除失败!");
        }
    }

    // ==================== 工具方法 ====================

    /** 读入一行并去掉首尾空白。所有文本输入都走这里，避免 "R001 " 这类误输入。 */
    private static String readLine(Scanner sc) {
        return sc.nextLine().trim();
    }

    private static void printLoans(Loan[] loans) {
        if (loans.length == 0) {
            System.out.println("（当前没有借阅记录）");
            return;
        }
        for (int i = 0; i < loans.length; i++) {
            System.out.println(loans[i]);
        }
    }

    /** 读一个合法范围内的菜单选项；非法输入提示后重试。 */
    private static int readChoice(Scanner sc, int max) {
        while (true) {
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= 0 && n <= max) {
                    return n;
                }
                System.out.println("无效选项，请重新输入：");
            }
            catch (NumberFormatException e) {
                System.out.println("请输入数字：");
            }
        }
    }
}
