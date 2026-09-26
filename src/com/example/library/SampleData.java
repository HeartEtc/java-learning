package com.example.library;

/**
 * 预置数据的工厂：构造一个"已经开馆"的图书馆，方便演示与手测。
 *
 * <p>把它从 Main 里拆出来，是为了让 Main 只负责"菜单交互"，不掺杂数据初始化。
 * 将来换成从文件读数据（P2 会做）时，只需要改这个类。
 */
final class SampleData {

    private SampleData() {
        // 工具类不需要被实例化
    }

    /** 建一个带管理员、读者和藏书的图书馆。 */
    static Library createLibrary() {
        Library library = new Library(50, 50, 50);

        // ---- 管理员（不通过注册产生，属于系统预置）----
        library.addUser(new Admin("M001", "王老师", 10));

        // ---- 读者 ----
        library.addUser(new Reader("R001", "张三", 3));
        library.addUser(new Reader("R002", "李四", 3));

        // ---- 藏书 ----
        library.addBook(new Book("B001", "9787111547426", "算法导论", "CLRS"));
        library.addBook(new Book("B002", "9787111213826", "Java编程思想", "Eckel"));
        library.addBook(new Book("B003", "9787111213827", "深入理解计算机系统", "Bryant"));
        library.addBook(new Book("B004", "9787115428028", "计算机网络：自顶向下方法", "Kurose"));
        library.addBook(new Book("B005", "9787111558422", "数据库系统概念", "Silberschatz"));
        library.addBook(new Book("B006", "9787302534639", "操作系统导论", "Arpaci-Dusseau"));
        library.addBook(new Book("B007", "9787115546081", "鸟哥的Linux私房菜", "鸟哥"));
        library.addBook(new Book("B008", "9787121362217", "Hadoop权威指南", "White"));

        // ---- 预置一条借阅记录，方便演示"还书"与"状态显示" ----
        library.borrowBook("B004", "R001");

        return library;
    }
}
