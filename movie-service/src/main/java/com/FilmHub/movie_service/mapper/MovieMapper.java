package com.FilmHub.movie_service.mapper;
import com.FilmHub.movie_service.payload.request.MovieRequest;
import org.mapstruct.Mapper;
import com.FilmHub.movie_service.models.Movie;
import com.FilmHub.movie_service.payload.dto.MovieDTO;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    /*
    Suponiendo que tienen mismo nombre de atributos
    sino :
        @Mapping(source = "title", target = "title")
        @Mapping(source = "director", target = "director")
        @Mapping(source = "releaseYear", target = "releaseYear")
        @Mapping(source = "actors", target = "actorIds")
     */

    MovieDTO toDTO(Movie movie);
    Movie toEntity(MovieDTO movieDTO);
    Movie toEntity(MovieRequest movieRequest);
}
