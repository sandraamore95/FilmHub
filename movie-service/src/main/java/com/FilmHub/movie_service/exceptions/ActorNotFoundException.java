package com.FilmHub.movie_service.exceptions;

public class ActorNotFoundException extends RuntimeException {
    // Constructor que recibe el mensaje de error
    public ActorNotFoundException(String message) {
        super(message);
    }
}
