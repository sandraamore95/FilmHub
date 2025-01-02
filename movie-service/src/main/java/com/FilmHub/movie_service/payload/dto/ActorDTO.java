package com.FilmHub.movie_service.payload.dto;
import jakarta.validation.constraints.NotBlank;

public class ActorDTO {

    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
