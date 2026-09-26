package com.example.library;
import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.UserNotFoundException;
import java.time.LocalDate;

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
               2. 找loan里对应记录并标记位置
               4. 从下标开始，后面的元素整体前移一位
               5. loans[loanCount - 1] = null，loanCount--
               6. book.setAvailable(true)
               7. return true
     */

    private final User[] users;
    private final Book[] books;
    private final Loan[] loans;
    private int bookCount;
    private int userCount;
    private int loanCount;
    private int loanDays = 30;

    public Library(int userCapacity, int bookCapacity, int loanCapacity) {
        this.users = new User[userCapacity];
        this.books = new Book[bookCapacity];
        this.loans = new Loan[loanCapacity];
        bookCount = 0;
        userCount = 0;
        loanCount = 0;
    }

    public boolean addBook(Book book) {
        if (bookCount >= books.length) {
            return false;
        }
        books[bookCount] = book;
        bookCount++;
        return true;
    }

    public boolean addUser(User user) {
        if (userCount >= users.length) {
            return false;
        }
        users[userCount] = user;
        userCount++;
        return true;
    }

    private boolean addLoan(Loan loan) {
        if (loanCount >= loans.length) {
            return false;
        }
        loans[loanCount] = loan;
        loanCount++;
        return true;
    }

    public void setLoanDays(int loanDays) {
        this.loanDays = loanDays;
    }

    public int getBookCount() {
        return bookCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public int getLoanCount() {
        return loanCount;
    }

    public int getLoanDays() {
        return loanDays;
    }

    public Loan[] getActiveLoans() {
        Loan[] result = new Loan[loanCount];
        for (int i = 0; i < loanCount; i++) {
            result[i] = loans[i];
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
        for (int i = 0; i < loanCount; i++) {
            if (user.equals(loans[i].getUser())) {
                n++;
            }
        }

        Loan[] result = new Loan[n];
        int j = 0;
        for (int i = 0; i < loanCount; i++) {
            if (user.equals(loans[i].getUser())) {
                result[j] = loans[i];
                j++;
            }
        }
        return result;
    }

    public Book[] getActiveBooks() {
        Book[] result = new Book[bookCount];
        for (int i = 0; i < bookCount; i++) {
            result[i] = books[i];
        }
        return result;
    }

    public User[] getActiveUsers() {
        User[] result = new User[userCount];
        for (int i = 0; i < userCount; i++) {
            result[i] = users[i];
        }
        return result;
    }

    public void printAllBooks() {
        for (int i = 0; i < bookCount; i++) {
            System.out.println((i + 1) + ". " + books[i].getTitle()
                    + " / " + books[i].getAuthor()
                    + (books[i].isAvailable() ? "  [可借]" : "  [已借出]"));
        }
    }

    public Book findBookById(String id) {
        for (int i = 0; i < bookCount; i++) {
            if (id.equals(books[i].getId())) {
                return books[i];
            }
        }
        throw new BookNotFoundException(id);
    }

    public User findUserById(String id) {
        for (int i = 0; i < userCount; i++) {
            if (id.equals(users[i].getId())) {
                return users[i];
            }
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

        if (loanCount >= loans.length){
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
               2. 找loan里对应记录并标记位置
               4. 从下标开始，后面的元素整体前移一位
               5. loans[loanCount - 1] = null，loanCount--
               6. book.setAvailable(true)
               7. return true
     */

    public boolean returnBook(String bookId, String userId){
        Book book = findBookById(bookId);
        User user = findUserById(userId);
        int index = -1;

        if (user.getBorrowCount() <= 0){
            return false;
        }

        for (int i = 0; i < loanCount; i++) {
            if (book.equals(loans[i].getBook())
                    && loans[i].getUser().getId().equals(userId)) {
                index = i;
                break;
            }
        }

        if (index == -1){
            return false;
        }

        for (int i = index; i < loanCount - 1; i++) {
            loans[i] = loans[i + 1];
        }

        book.setAvailable(true);
        user.setBorrowCount(user.getBorrowCount() - 1);
        loans[loanCount - 1] = null;
        loanCount--;
        return true;
    }

    public boolean deleteBook(User operator, String bookId) {
        if (!(operator instanceof Admin)) {
            return false;
        }

        Book book = findBookById(bookId);
        int index = -1;
        for (int i = 0; i < bookCount; i++) {
            if (book.equals(books[i])
                    && book.isAvailable()) {
                index = i;
                break;
            }
        }

        if (index == -1){
            return false;
        }

        for (int i = index; i < bookCount - 1; i++) {
            books[i] = books[i + 1];
        }

        books[bookCount - 1] = null;
        bookCount--;
        return true;
    }

    public boolean deleteUser(User operator, String userId) {
        User user = findUserById(userId);
        if (!(operator instanceof Admin)){
            return false;
        }

        if (user.getBorrowCount() > 0){
            return false;
        }

        int index = -1;
        for (int i = 0; i < userCount; i++) {
            if (user.equals(users[i])) {
                index = i;
                break;
            }
        }

        if (index == -1){
            return false;
        }

        for (int i = index; i < userCount - 1; i++) {
            users[i] = users[i + 1];
        }

        users[userCount - 1] = null;
        userCount--;
        return true;
    }
}
