package com.FilmHub.movie_service.exceptions;

public class MovieNotFoundException extends RuntimeException {
    // Constructor que recibe el mensaje de error
    public MovieNotFoundException(String message) {
        super(message);
    }
}
