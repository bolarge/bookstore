package com.demo.bookstore.exception;

public class BookStoreException extends RuntimeException {

  public BookStoreException(String message, Throwable cause) {
    super(message, cause);
  }
}
