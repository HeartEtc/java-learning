package com.example.library.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);          // ← 交给父类保存，这样 getMessage() 才拿得到
    }
}