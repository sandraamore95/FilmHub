package com.FilmHub.payload.request;
import jakarta.validation.constraints.NotNull;
public class FavoriteRequest {

    @NotNull(message = "User ID is required")
    private Long userId;
    @NotNull(message = "MovieId is required")
    private Long movieId;

    // Constructor vacío
    public FavoriteRequest() {
    }

    // Constructor con parámetros
    public FavoriteRequest(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
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