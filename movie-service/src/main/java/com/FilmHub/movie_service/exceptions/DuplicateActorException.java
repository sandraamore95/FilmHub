package com.FilmHub.movie_service.exceptions;

public class DuplicateActorException extends RuntimeException {
    public DuplicateActorException(String message) {
        super(message);
    }
}
