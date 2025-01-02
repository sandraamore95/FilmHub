package com.FilmHub.movie_service.payload.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class MovieDTO {

    private Long id;

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El director es obligatorio")
    private String director;

    @Min(value = 1888, message = "El año de estreno no puede ser anterior a 1888")
    private int releaseYear;

    private List<Long> actorIds;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title) {
        this.title = title;
    }

    public  String getDirector() {
        return director;
    }

    public void setDirector( String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear( int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<Long> getActorIds() {
        return actorIds;
    }

    public void setActorIds(List<Long> actorIds) {
        this.actorIds = actorIds;
    }
}
