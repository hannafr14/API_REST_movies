package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<String> handleMovieNotFound(MovieNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(YearNotFoundException.class)
    public ResponseEntity<String> handleYearNotFound(YearNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<String> handleGenreNotFound(GenreNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ActorNotFoundException.class)
    public ResponseEntity<String> handleActorNotFound(ActorNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
