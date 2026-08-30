package com.example.demo.exceptions;

public class YearNotFoundException extends RuntimeException {

    public YearNotFoundException(Long id) {
        super("Year with id " + id + " not found");
    }
}