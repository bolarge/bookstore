package com.demo.bookstore.exception;

import java.util.List;

public class BookStoreException extends RuntimeException {

  public BookStoreException(String message, Throwable cause) {
    super(message, cause);
  }

  public BookStoreException(List<String> errors) {
    super();
  }
}
