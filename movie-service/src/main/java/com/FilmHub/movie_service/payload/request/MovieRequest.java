package com.FilmHub.movie_service.payload.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class MovieRequest {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El director es obligatorio")
    private String director;

    @Min(value = 1888, message = "El año de estreno no puede ser anterior a 1888")
    private int releaseYear;

    // Constructors
    public MovieRequest() {}

    public MovieRequest(String title, String director, int releaseYear) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
    }

    // Getters and Setters
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

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
