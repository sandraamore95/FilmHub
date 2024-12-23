package com.FilmHub.movie_service.mapper;
import org.mapstruct.Mapper;
import com.FilmHub.movie_service.models.Movie;
import com.FilmHub.movie_service.payload.MovieDTO;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(source = "title", target = "title")
    @Mapping(source = "director", target = "director")
    @Mapping(source = "releaseYear", target = "releaseYear")
    MovieDTO toDTO(Movie movie);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "director", target = "director")
    @Mapping(source = "releaseYear", target = "releaseYear")
    Movie toEntity(MovieDTO movieDTO);
}
