package com.FilmHub.movie_service.payload;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

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

    //@Data -> constructores, getter, setter


    public MovieDTO() {
    }

    public MovieDTO(Long id, String title, String director, int releaseYear) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
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

}
