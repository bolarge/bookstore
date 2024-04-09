package com.demo.bookstore.utils;

import lombok.Data;

@Data
public class GenericResponse<T> {
    private String message;
    private T data;
}
