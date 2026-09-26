package com.example.library;

import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.UserNotFoundException;

import java.util.Scanner;

/**
 * P1 图书管理系统的界面
 */
public class Main {
    public static void main(String[] args) {
        Library library = SampleData.createLibrary();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("图书馆系统\n功能列表:\n1:注册读者\n2:查看书籍\n3:登录读者\n0:退出");
            int choice = readChoice(sc, 3);
            if (choice == 0) break;
            try {
                switch (choice) {
                    case 1 -> {
                        System.out.println("读者注册\n请输入id:");
                        String userid = sc.nextLine();
                        System.out.println("请输入用户名:");
                        String username = sc.nextLine();
                        if (library.addUser(new Reader(userid, username, 3))) {
                            System.out.println("注册成功");
                        } else {
                            System.out.println("注册失败：用户已满");
                        }
                    }
                    case 2 -> {
                        library.printAllBooks();
                    }
                    case 3 -> {
                        System.out.println("读者登录\n请输入id");
                        String userid = sc.nextLine();
                        System.out.println("请输入用户名:");
                        String username = sc.nextLine();

                            if (library.findUserById(userid).getName().equals(username)) {
                                boolean loggedIn = true;                  // ★ 会话标志
                                System.out.println("登录成功,欢迎," + username);
                                while (loggedIn) {
                                    if (!(library.findUserById(userid) instanceof Admin)) {
                                        System.out.println("图书馆系统     当前登录:" + username
                                                + "\n功能列表:\n1:查看书籍\n2:借阅书籍\n3:归还书籍\n4:我的借阅"
                                                + "\n0:注销");
                                        int readerchoice = readChoice(sc, 4);
                                        if (readerchoice == 0) {
                                            loggedIn = false;             // ★ 标记"结束会话"
                                            continue;                     // 或直接 else 分支里写 switch
                                        }
                                        switch (readerchoice) {
                                            case 1 -> {
                                                library.printAllBooks();
                                            }

                                            case 2 -> {
                                                System.out.println("借阅书籍\n请输入书籍id:");
                                                String borrowbookid = sc.nextLine();
                                                if (library.borrowBook(borrowbookid, userid)) {
                                                    System.out.println("借阅成功!默认还书期限为30天!");
                                                } else {
                                                    System.out.println("借阅失败!");
                                                }
                                            }

                                            case 3 -> {
                                                System.out.println("归还书籍\n请输入书籍id:");
                                                String returnbookid = sc.nextLine();
                                                System.out.println("请输入归还用户id:");
                                                String returnid = sc.nextLine();
                                                if (library.returnBook(returnbookid, returnid)) {
                                                    System.out.println("归还成功!欢迎下次借阅!");
                                                } else {
                                                    System.out.println("归还失败!");
                                                }
                                            }

                                            case 4 -> {
                                                Loan[] loans = library.getActiveUserLoans(userid);
                                                for (int i = 0; i < loans.length; i++) {
                                                    System.out.println(loans[i]);
                                                }
                                            }

                                        }
                                    }
                                    if (library.findUserById(userid) instanceof Admin) {
                                        System.out.println("图书馆系统     当前登录(管理员权限):" + username
                                                + "\n功能列表:\n1:查看书籍\n2:借阅书籍\n3:归还书籍\n4:我的借阅"
                                                + "\n5:添加书籍\n6:删除书籍\n7:查找用户\n8:删除用户\n0:注销");
                                        int adminchoice = readChoice(sc, 8);
                                        if (adminchoice == 0) {
                                            loggedIn = false;             // ★
                                            continue;
                                        }
                                        switch (adminchoice) {
                                            case 1 -> {
                                                library.printAllBooks();
                                            }

                                            case 2 -> {
                                                System.out.println("借阅书籍\n请输入书籍id:");
                                                String borrowbookid = sc.nextLine();
                                                if (library.borrowBook(borrowbookid, userid)) {
                                                    System.out.println("借阅成功!默认还书期限为30天!");
                                                } else {
                                                    System.out.println("借阅失败!");
                                                }
                                            }

                                            case 3 -> {
                                                System.out.println("归还书籍\n请输入书籍id:");
                                                String returnbookid = sc.nextLine();
                                                System.out.println("请输入归还用户id:");
                                                String returnid = sc.nextLine();
                                                if (library.returnBook(returnbookid, returnid)) {
                                                    System.out.println("归还成功!欢迎下次借阅!");
                                                } else {
                                                    System.out.println("归还失败!");
                                                }
                                            }

                                            case 4 -> {
                                                Loan[] loans = library.getActiveUserLoans(userid);
                                                for (int i = 0; i < loans.length; i++) {
                                                    System.out.println(loans[i]);
                                                }
                                            }

                                            case 5 -> {
                                                System.out.println("添加书籍\n请输入书籍id:");
                                                String newbookid = sc.nextLine();
                                                System.out.println("请输入书籍isbn码:");
                                                String newbookisbn = sc.nextLine();
                                                System.out.println("请输入书籍名称:");
                                                String newbookname = sc.nextLine();
                                                System.out.println("请输入书籍作者:");
                                                String newbookauthor = sc.nextLine();
                                                if (library.addBook(new Book(newbookid, newbookisbn,
                                                        newbookname, newbookauthor))) {
                                                    System.out.println("添加成功!");
                                                } else {
                                                    System.out.println("添加失败!");
                                                }
                                            }

                                            case 6 -> {
                                                System.out.println("删除书籍\n权限核验:\n请输入您的用户id:");
                                                String deleteid = sc.nextLine();
                                                System.out.println("请输入书籍id:");
                                                String deletebookid = sc.nextLine();
                                                if (library.deleteBook(library.findUserById(deleteid), deletebookid)) {
                                                    System.out.println("删除成功!");
                                                } else {
                                                    System.out.println("删除失败!");
                                                }
                                            }

                                            case 7 -> {
                                                System.out.println("查询用户\n请输入目标用户id:");
                                                String finduserid = sc.nextLine();
                                                System.out.println(library.findUserById(finduserid).toString());
                                            }

                                            case 8 -> {
                                                System.out.println("删除用户(谨慎操作!)\n权限核验:\n请输入您的用户id:");
                                                String deleteid = sc.nextLine();
                                                System.out.println("请输入删除目标用户id:");
                                                String deleteuserid = sc.nextLine();
                                                if (library.deleteUser(library.findUserById(deleteid), deleteuserid)) {
                                                    System.out.println("删除成功!");
                                                } else {
                                                    System.out.println("删除失败!");
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                System.out.println("登录失败");
                                break;
                            }

                    }
                    default -> {
                        System.out.println("无效选项! ");
                    }
                }
            }
            catch (UserNotFoundException | BookNotFoundException e) {
                System.out.println("操作失败: " + e.getMessage());
            }
            catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入数字");
            }
        }
    }

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
