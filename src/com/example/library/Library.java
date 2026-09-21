package com.example.library;
import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.UserNotFoundException;

public class Library {
    //图书的存储,新建指定长度的数组,数组具体内容见Book
    /*
    额外定义     addBook        加书
               deleteBook     删除书
               addReader      添加用户
               deleteReader   删除用户
               borrowBook         借阅
               returnBook         还书
     */

    private final User[] users;
    private final Book[] books;
    private final Loan[] loans;
    private int bookCount;
    private int userCount;
    private int loanCount;

    public Library(int userCapacity, int bookCapacity, int loanCapacity) {
        this.users = new User[userCapacity];
        this.books = new Book[bookCapacity];
        this.loans = new Loan[loanCapacity];
        bookCount = 0;
        userCount = 0;
        loanCount = 0;
    }

    public boolean addBook(Book book) {
        if (bookCount >= books.length) {     // 直接用 count 判断满没满
            return false;
        }
        books[bookCount] = book;
        bookCount++;
        return true;
    }          // 加书：满了返回 false，成功返回 true

    public boolean addUser(User user) {
        if (userCount >= users.length) {     // 直接用 count 判断满没满
            return false;
        }
        users[userCount] = user;
        userCount++;
        return true;
    }          // 加用户：同上

    public int getBookCount() {
        return bookCount;
    }                  // 返回 bookCount

    public int getUserCount() {
        return userCount;
    }                  // 返回 userCount

    public Book findBookById(String id) {
        for (int i = 0; i < bookCount; i++) {
            if (id.equals(books[i].getId())) {
                return books[i];
            }
        }
        throw new BookNotFoundException(id);
    }        // 找到返回 Book，找不到抛 BookNotFoundException
    public User findUserById(String id) {
        for (int i = 0; i < userCount; i++) {
            if (id.equals(users[i].getId())) {
                return users[i];
            }
        }
        throw new UserNotFoundException(id);
    }        // 找到返回 User，找不到抛 UserNotFoundException
}
