package com.example.library;
import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.UserNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    //图书的存储,新建指定长度的数组,数组具体内容见Book
    /*
    额外定义     addBook        加书
               deleteBook     删除书
               addReader      添加用户
               deleteReader   删除用户
               borrowBook         借阅
               工作步骤:
               1.找书   findBookById
               2.找人   findUserById
               3.检查借阅情况       抛false提示书被借走
                                  抛false提示容量满
               4.设定书为借阅状态
               5.存储记录          到期时间通过配置修改
               6.返回成功(这要求borrowBook为boolean类型)
               returnBook         还书
               工作步骤:
               1. 找书   findBookById
               2. 找loan里对应记录并标记位置（不再需要）
               4. 从下标开始，后面的元素整体前移一位（不再需要）
               5. loans[loanCount - 1] = null，loanCount--（不再需要）
               6. book.setAvailable(true)
               7. return true

               10.11更新:改变数组为list和arraylist
               重构代码
     */

    private final List<User> users = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();
    private final Map<String, Book> bookIndex = new HashMap<>();
    private final Map<String, User> userIndex = new HashMap<>();
    private int loanDays = 30;

    public Library() {
    }

    public boolean addBook(Book book) {
        String id = book.getId();
        if (bookIndex.containsKey(id)) {
            return false;
        }
        books.add(book);
        bookIndex.put(id, book);
        return true;
    }

    public boolean addUser(User user) {
        String id = user.getId();
        if (userIndex.containsKey(id)) {
            return false;
        }
        users.add(user);
        userIndex.put(user.getId(), user);
        return true;
    }

    public boolean addLoan(Loan loan) {
        loans.add(loan);
        return true;
    }

    public void setLoanDays(int loanDays) {
        this.loanDays = loanDays;
    }

    public int getBookCount() {
        return books.size();
    }

    public int getUserCount() {
        return users.size();
    }

    public int getLoanCount() {
        return loans.size();
    }

    public int getLoanDays() {
        return loanDays;
    }

    public Loan[] getActiveLoans() {
        Loan[] result = new Loan[loans.size()];
        for (int i = 0; i < loans.size(); i++) {
            result[i] = loans.get(i);
        }
        return result;
    }

    /**
     * 取某个用户当前在借的记录。
     *
     * 数组长度必须等于"实际匹配数"，所以只能遍历两遍：
     * 第一遍数出条数用于分配数组，第二遍才装填。
     * 注意装填用独立计数器 j（源下标 i 与目标下标 j 不能混用，否则结果中间会留下 null）。
     */
    public Loan[] getActiveUserLoans(String userid) {
        User user = findUserById(userid);        // 提到循环外，避免每次迭代都做一次 O(n) 查找

        int n = 0;
        for (int i = 0; i < loans.size(); i++) {
            if (user.equals(loans.get(i).getUser())) {
                n++;
            }
        }

        Loan[] result = new Loan[n];
        int j = 0;
        for (int i = 0; i < loans.size(); i++) {
            if (user.equals(loans.get(i).getUser())) {
                result[j] = loans.get(i);
                j++;
            }
        }
        return result;
    }

    public Book[] getActiveBooks() {
        Book[] result = new Book[books.size()];
        for (int i = 0; i < books.size(); i++) {
            result[i] = books.get(i);
        }
        return result;
    }

    public User[] getActiveUsers() {
        User[] result = new User[users.size()];
        for (int i = 0; i < users.size(); i++) {
            result[i] = users.get(i);
        }
        return result;
    }

    public void printAllBooks() {
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i).getTitle()
                    + " / " + books.get(i).getAuthor()
                    + (books.get(i).isAvailable() ? "  [可借]" : "  [已借出]"));
        }
    }

    public Book findBookById(String id) {
        if (bookIndex.containsKey(id)) {
                return bookIndex.get(id);
        }
        throw new BookNotFoundException(id);
    }

    public User findUserById(String id) {
        if (userIndex.containsKey(id)) {
            return userIndex.get(id);
        }
        throw new UserNotFoundException(id);
    }

    public boolean borrowBook(String bookId, String userId){
        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (!book.isAvailable()){
            return false;
        }

        if (user.getBorrowCount() >= user.getLoanLimit()){
            return false;
        }

        book.setAvailable(false);
        user.setBorrowCount(user.getBorrowCount() + 1);
        LocalDate borrowDate = LocalDate.now();
        addLoan(new Loan(book,user,borrowDate,
                borrowDate.plusDays(loanDays)));
        return true;
    }

    /*
    工作步骤:
               1. 找书   findBookById
               2. 找loan里对应记录并标记位置（不再需要）
               4. 从下标开始，后面的元素整体前移一位（不再需要）
               5. loans[loanCount - 1] = null，loanCount--（不再需要）
               6. book.setAvailable(true)
               7. return true
     */

    public boolean returnBook(String bookId, String userId){
        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (user.getBorrowCount() <= 0){
            return false;
        }

        for (int i = 0; i < loans.size(); i++) {
            Loan l = loans.get(i);
            if (l.getBook().equals(book) && l.getUser().equals(user)) {
                loans.remove(i);                 // 删借阅记录（传 int → 按下标删）
                book.setAvailable(true);
                user.setBorrowCount(user.getBorrowCount() - 1);
                return true;
            }
        }

        return false;        // 没有"这本书 + 这个人"的借阅记录 → 还书失败
    }

    public boolean deleteBook(User operator, String bookId) {
        if (!(operator instanceof Admin)) {   // 1. 先授权（不碰数据）
            return false;
        }
        Book book = findBookById(bookId);     // 2. 再查存在性（不存在 → 抛异常）
        books.remove(book);                   // 3. 最后执行
        bookIndex.remove(bookId);
        return true;
    }

    public boolean deleteUser(User operator, String userId) {
        if (!(operator instanceof Admin)){
            return false;
        }

        User user = findUserById(userId);
        if (user.getBorrowCount() > 0){
            return false;
        }

        users.remove(user);
        userIndex.remove(userId);
        return true;
    }
}
