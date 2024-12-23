package com.FilmHub.models;
import jakarta.persistence.*;

@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; // ID del usuario
    private Long movieId; // ID de la película

    // Constructor vacío
    public Favorite() {
    }

    // Constructor para crear un favorito
    public Favorite(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    // Getters y setters
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
