package com.FilmHub.payload.dto;

public class FavoriteDTO {
    private Long id;
    private Long userId;
    private Long movieId;

    public FavoriteDTO(Long id, Long userId, Long movieId) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
