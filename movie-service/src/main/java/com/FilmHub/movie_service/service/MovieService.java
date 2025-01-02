package com.FilmHub.movie_service.service;
import com.FilmHub.movie_service.exceptions.ActorNotFoundException;
import com.FilmHub.movie_service.exceptions.DuplicateMovieException;
import com.FilmHub.movie_service.exceptions.MovieNotFoundException;
import com.FilmHub.movie_service.mapper.MovieMapper;
import com.FilmHub.movie_service.models.Actor;
import com.FilmHub.movie_service.models.Movie;
import com.FilmHub.movie_service.payload.dto.MovieDTO;
import com.FilmHub.movie_service.payload.request.MovieRequest;
import com.FilmHub.movie_service.repository.ActorRepository;
import com.FilmHub.movie_service.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieMapper movieMapper;
    private final Logger logger = LoggerFactory.getLogger(MovieService.class);


    @Autowired
    public MovieService(MovieRepository movieRepository,MovieMapper movieMapper,ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository=actorRepository;
        this.movieMapper=movieMapper;
    }

    // Obtener todas las películas
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDTO> movieDTOs = movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
        return movieDTOs;
    }

    // Obtener una película por su ID
    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Película con ID " + id + " no encontrada"));
        return movieMapper.toDTO(movie);  // Convertir la entidad Movie a DTO
    }

    // Obtener una película por su titulo
    public MovieDTO getMovieByTitle(String title) {
        String trimmedTitle = title.trim();
        Movie movie = movieRepository.findByTitleIgnoreCase(trimmedTitle)
                .orElseThrow(() -> {
                    return new MovieNotFoundException("Película con título '" + title + "' no encontrada");
                });
        return movieMapper.toDTO(movie);
    }

    //Crear pelicula
    public MovieDTO createMovie(MovieRequest movieRequest) {
            // Verificar si la película ya existe
            if (movieRepository.findByTitleIgnoreCase(movieRequest.getTitle()).isPresent()) {
                throw new DuplicateMovieException("La película con el título '" + movieRequest.getTitle() + "' ya existe.");
            }
            Movie movie = movieMapper.toEntity(movieRequest);// Convertir MovieRequest a entidad Movie
            Movie savedMovie = movieRepository.save(movie); //guarda en BD
            return movieMapper.toDTO(savedMovie);

    }

    //Update peliucula
    public MovieDTO updateMovie(Long id, MovieRequest movieRequest) {
        try {

            // Verificar que existe la película
            Movie existingMovie = movieRepository.findById(id)
                    .orElseThrow(() -> new MovieNotFoundException("Película con ID " + id + " no encontrada"));

            // Actualizar solo los campos que se proporcionan en el DTO
            if (StringUtils.hasText(movieRequest.getTitle())) {
                existingMovie.setTitle(movieRequest.getTitle());
            }
            if (movieRequest.getReleaseYear() > 0) {
                existingMovie.setReleaseYear(movieRequest.getReleaseYear());
            }

            // Guardar la película actualizada en el repositorio
            Movie updatedMovie = movieRepository.save(existingMovie);
            return movieMapper.toDTO(updatedMovie);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateMovieException("La película con el título '" + movieRequest.getTitle() + "' ya existe.");
        }
    }

    //Asociar actor a pelicula
    @Transactional
    public MovieDTO addActorsToMovie(Long movieId, Set<Long> actorIds) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Película con ID " + movieId + " no encontrada"));

        Set<Actor> actors = new HashSet<>();
        for (Long actorId : actorIds) {
            Actor actor = actorRepository.findById(actorId)
                    .orElseThrow(() -> new ActorNotFoundException("Actor con ID " + actorId + " no encontrado"));
            actors.add(actor);
        }
        movie.setActors(actors);
        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toDTO(savedMovie);
    }

    // Eliminar una película
    public void deleteMovie(Long id) {
            // Verificar que existe la película
            Movie existingMovie = movieRepository.findById(id)
                    .orElseThrow(() -> new MovieNotFoundException("Película con ID " + id + " no encontrada"));

       this.movieRepository.delete(existingMovie);
    }

}
