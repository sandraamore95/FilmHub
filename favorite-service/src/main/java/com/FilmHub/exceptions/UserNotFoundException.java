package com.FilmHub.exceptions;

public class UserNotFoundException extends RuntimeException {
    // Constructor que recibe el mensaje de error
    public UserNotFoundException(String message) {
        super(message);
    }
}
