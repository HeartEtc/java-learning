package com.example.library;

import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.UserNotFoundException;

public class LookupTest {

    static int pass = 0, fail = 0;

    static void eq(String what, Object expected, Object actual) {
        boolean ok = java.util.Objects.equals(expected, actual);
        if (ok) pass++; else fail++;
        System.out.printf("%s %-40s 期望=%-10s 实际=%s%n", ok ? "✅" : "❌", what, expected, actual);
    }

    static void boom(String what, Class<? extends RuntimeException> ex, Runnable r) {
        try {
            r.run();
            fail++;
            System.out.printf("❌ %-40s 期望抛 %s，实际没抛%n", what, ex.getSimpleName());
        } catch (RuntimeException e) {
            boolean ok = ex.isInstance(e);
            if (ok) pass++; else fail++;
            System.out.printf("%s %-40s %s%n", ok ? "✅" : "❌", what, e.getClass().getSimpleName());
        }
    }

    public static void main(String[] args) {

        // ===== 1. 空 id：测构造器，不是测 Library =====
        System.out.println("=== 1. 空 id（Book / User 构造器）===");
        boom("Book(null id)",   IllegalArgumentException.class, () -> new Book(null, "978-1", "书", "作者"));
        boom("Book(空串 id)",   IllegalArgumentException.class, () -> new Book("", "978-1", "书", "作者"));
        boom("Book(空白 id)",   IllegalArgumentException.class, () -> new Book("   ", "978-1", "书", "作者"));
        boom("Book(null isbn)", IllegalArgumentException.class, () -> new Book("B001", null, "书", "作者"));
        boom("Book(空 title)",  IllegalArgumentException.class, () -> new Book("B001", "978-1", "", "作者"));
        boom("Book(空 author)", IllegalArgumentException.class, () -> new Book("B001", "978-1", "书", ""));
        boom("Reader(空 id)",   IllegalArgumentException.class, () -> new Reader("", "张三", 3));
        boom("Reader(空 name)", IllegalArgumentException.class, () -> new Reader("R001", "  ", 3));
        boom("Reader(loanLimit=0)",  IllegalArgumentException.class, () -> new Reader("R001", "张三", 0));
        boom("Reader(loanLimit=-1)", IllegalArgumentException.class, () -> new Reader("R001", "张三", -1));

        // ===== 2. 空 id 不该污染书库 =====
        System.out.println("\n=== 2. 构造失败不污染库 ===");
        Library lib = new Library();
        try { lib.addBook(new Book(null, "978-1", "书", "作者")); } catch (RuntimeException ignore) {}
        eq("书数为 0", 0, lib.getBookCount());

        // ===== 3. ★ Book.equals 的语义 bug =====
        System.out.println("\n=== 3. Book.equals 是否包含 available ===");
        Book b1 = new Book("B001", "978-1", "算法导论", "CLRS");
        Book b2 = new Book("B001", "978-1", "算法导论", "CLRS");
        eq("同一本书两对象 equals", true, b1.equals(b2));
        eq("hashCode 一致", b1.hashCode(), b2.hashCode());
        b1.setAvailable(false);                       // 模拟 b1 被借出
        eq("借出后仍应 equals（同一本书）", true, b1.equals(b2));   // ← 这里会挂
        eq("借出后 hashCode 不变", b2.hashCode(), b1.hashCode());

        // ===== 4. equals 的传递方向（对称性）=====
        System.out.println("\n=== 4. 借出书上架再归还的完整链路 ===");
        Library f = new Library();
        f.addBook(new Book("B001", "978-1", "算法导论", "CLRS"));
        f.addUser(new Reader("R001", "张三", 3));
        eq("借出", true, f.borrowBook("B001", "R001"));
        eq("记录数 1", 1, f.getLoanCount());
        eq("归还", true, f.returnBook("B001", "R001"));          // ← 失败就是 equals 惹的祸
        eq("记录数 0", 0, f.getLoanCount());
        eq("书恢复可借", true, f.findBookById("B001").isAvailable());

        // ===== 5. 库里对象的引用与判等语义 =====
        System.out.println("\n=== 5. 同一本书的两个对象 ===");
        Library g = new Library();
        Book added = new Book("B001", "978-1", "算法导论", "CLRS");
        g.addBook(added);
        g.addUser(new Reader("R001", "张三", 3));

        Book viaFind = g.findBookById("B001");
        eq("findBookById 返回同一引用", true, added == viaFind);

// 关键：构造一个"内容相同但是另一个对象"的书
        Book twin = new Book("B001", "978-1", "算法导论", "CLRS");
        eq("孪生对象与库中书 equals", true, added.equals(twin));

        eq("借出", true, g.borrowBook("B001", "R001"));
        eq("归还（用孪生对象走查找路径）", true, g.returnBook(twin.getId(), "R001"));
        eq("记录数 0", 0, g.getLoanCount());
        eq("书恢复可借", true, g.findBookById("B001").isAvailable());

        // ===== 6. 删除的权限语义（两条路径不一致）=====
        System.out.println("\n=== 6/7. 删除权限矩阵（含状态快照）===");
        Library v = new Library();
        Admin  ad = new Admin("M001", "王老师", 5);
        Reader rd = new Reader("R001", "张三", 3);
        v.addUser(ad);
        v.addUser(rd);
        v.addBook(new Book("B001", "978-1", "算法导论", "CLRS"));

        int books0 = v.getBookCount(), users0 = v.getUserCount();

        eq("读者删已存在的书：拒绝", false, v.deleteBook(rd, "B001"));
        eq("拒绝后书数不变",        books0, v.getBookCount());          // ★ 关键断言
        eq("读者删不存在的书：拒绝",  false, v.deleteBook(rd, "X999"));
        eq("仍拒绝且书数不变",       books0, v.getBookCount());

        eq("读者删用户：拒绝",      false, v.deleteUser(rd, "R001"));
        eq("拒绝后用户数不变",       users0, v.getUserCount());

        boom("管理员删不存在的书：抛异常", BookNotFoundException.class, () -> v.deleteBook(ad, "X999"));
        eq("抛异常后书数不变",       books0, v.getBookCount());

        eq("管理员删已存在的书：成功", true, v.deleteBook(ad, "B001"));
        eq("书数归零",              0, v.getBookCount());
        boom("删除后再查：抛异常",   BookNotFoundException.class, () -> v.findBookById("B001"));

        // ===== 8. 借阅上限与借出状态 =====
        System.out.println("\n=== 8. 限额与状态 ===");
        Library h = new Library();
        h.addBook(new Book("B001", "978-1", "书一", "A"));
        h.addBook(new Book("B002", "978-2", "书二", "B"));
        h.addUser(new Reader("R001", "张三", 1));     // 限额 1
        eq("借第一本", true, h.borrowBook("B001", "R001"));
        eq("超额借第二本", false, h.borrowBook("B002", "R001"));
        eq("第二本仍可借", true, h.findBookById("B002").isAvailable());   // ← 失败操作不能改状态
        eq("第一本已借出", false, h.findBookById("B001").isAvailable());
        eq("记录数仍为 1", 1, h.getLoanCount());

        // ===== 9. 失败操作必须不改变任何状态 =====
        System.out.println("\n=== 9. 失败操作原子性 ===");
        Library k = new Library();
        k.addBook(new Book("B001", "978-1", "算法导论", "CLRS"));
        k.addUser(new Reader("R001", "张三", 3));
        boom("借不存在的书", BookNotFoundException.class, () -> k.borrowBook("X999", "R001"));
        boom("不存在的用户借书", UserNotFoundException.class, () -> k.borrowBook("B001", "X999"));
        eq("记录数 0", 0, k.getLoanCount());
        eq("书仍可借", true, k.findBookById("B001").isAvailable());

        // ===== 10. 借来借去状态不漂移（借还 10 轮）=====
        System.out.println("\n=== 10. 反复借还 10 轮 ===");
        Library loop = new Library();
        loop.addBook(new Book("B001", "978-1", "算法导论", "CLRS"));
        loop.addUser(new Reader("R001", "张三", 3));
        int broken = 0;
        for (int i = 0; i < 10; i++) {
            if (!loop.borrowBook("B001", "R001")) broken++;
            if (!loop.returnBook("B001", "R001")) broken++;
        }
        eq("20 次操作全部成功", 0, broken);
        eq("最终借阅记录为 0", 0, loop.getLoanCount());
        eq("最终借出计数为 0", 0, loop.findUserById("R001").getBorrowCount());
        eq("最终书可借", true, loop.findBookById("B001").isAvailable());

        System.out.printf("%n结果：通过 %d / 失败 %d%n", pass, fail);
    }
}