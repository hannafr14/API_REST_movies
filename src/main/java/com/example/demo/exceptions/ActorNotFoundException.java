package com.example.demo.exceptions;

public class ActorNotFoundException extends RuntimeException {

    public ActorNotFoundException(Long id) {
        super("Actor with id " + id + " not found");
    }
}
