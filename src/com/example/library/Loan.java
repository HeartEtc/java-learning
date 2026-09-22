package com.example.library;
import java.time.LocalDate;

public class Loan {
    /*
    借阅系统的借阅数据存储处
    存储字段
    book          借阅书籍
    user          借阅人
    borrowDate    借阅时期
    dueDate       还书时期

     */


    private final LocalDate  borrowDate;    // 年、月、日 三个 int
    private final LocalDate dueDate;
    private final Book book;
    private final User user;


    public Loan(Book book, User user, LocalDate borrowDate, LocalDate dueDate){
        if (book == null) {
            throw new IllegalArgumentException("invalid book!");
        }
        else if (user == null) {
            throw new IllegalArgumentException("invalid user!");
        }
        else if (borrowDate == null) {
            throw new IllegalArgumentException("invalid borrowDate!");
        }
        else if (dueDate == null) {
            throw new IllegalArgumentException("invalid duedate!");
        }
        else if (!borrowDate.isBefore(dueDate)) {
            throw new IllegalArgumentException("borrowdate must be earlier than duedate");
        }
        else {
            this.book = book;
            this.user = user;
            this.borrowDate = borrowDate;
            this.dueDate = dueDate;
        }
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return "Loan{book=" + book.getTitle() + ", user=" + user.getName()
                + ", borrowDate=" + borrowDate + ", dueDate=" + dueDate + "}";
    }
}
