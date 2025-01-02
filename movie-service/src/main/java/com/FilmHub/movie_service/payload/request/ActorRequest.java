package com.FilmHub.movie_service.payload.request;
import jakarta.validation.constraints.NotBlank;

public class ActorRequest {

    @NotBlank(message = "El nombre del actor no puede estar vacío")
    private String name;

    //Constructors
    public ActorRequest() {
    }

    public ActorRequest(String name) {
        this.name = name;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
