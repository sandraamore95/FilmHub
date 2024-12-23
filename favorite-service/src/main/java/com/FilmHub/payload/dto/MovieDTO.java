package com.FilmHub.payload.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MovieDTO {
    @NotBlank(message = "ID no puede estar vacío")
    private Long id;

    @NotBlank(message = "Nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String title;

    @Size(max = 255, message = "el director no puede exceder los 255 caracteres")
    private String director;

    public MovieDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
