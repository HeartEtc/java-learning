package com.example.library;

import java.util.Objects;

public class Book {
    //书本身,存储4种字段,isbn         国际标准书号  用String
    //               ,title        标题        用string
    //               ,author       作者        用string
    //               ,available    可用状态     用boolean

    private final String isbn;
    private final String id;
    private final String title;
    private final String author;
    private boolean available;

    public Book(String id, String isbn,String title, String author) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("invalid id!");
        }
        else if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("invalid isbn!");
        }
        else if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("invalid title name!");
        }
        else if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("invalid author name!");
        }
        else {
            this.id = id;
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.available = true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return available == book.available
                && Objects.equals(isbn, book.isbn)
                && Objects.equals(id, book.id)
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, id, title, author, available);
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getId() {
        return id;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    @Override
    public String toString() {
        String s = "Book{isbn='" + isbn + "', id='" + id + "', title='" + title
                + "', author='" + author + "', available=" + available + "}";
        return s;
    }
}
