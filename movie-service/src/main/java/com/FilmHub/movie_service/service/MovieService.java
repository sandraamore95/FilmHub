package com.FilmHub.movie_service.service;
import com.FilmHub.movie_service.exceptions.DuplicateMovieException;
import com.FilmHub.movie_service.exceptions.MovieNotFoundException;
import com.FilmHub.movie_service.mapper.MovieMapper;
import com.FilmHub.movie_service.models.Movie;
import com.FilmHub.movie_service.payload.MovieDTO;
import com.FilmHub.movie_service.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final Logger logger = LoggerFactory.getLogger(MovieService.class);


    @Autowired
    public MovieService(MovieRepository movieRepository,MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
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

    //Crear pelicula
    public MovieDTO createMovie(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.toEntity(movieDTO); // Convertir MovieDTO a entidad Movie
            Movie savedMovie = movieRepository.save(movie);
            return movieMapper.toDTO(savedMovie);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateMovieException("La película con el título '" + movieDTO.getTitle() + "' ya existe.");
        }
    }

    //Update peliucula
    public MovieDTO updateMovie(Long id, MovieDTO movieDTO) {
        try {

            // Verificar que existe la película
            Movie existingMovie = movieRepository.findById(id)
                    .orElseThrow(() -> new MovieNotFoundException("Película con ID " + id + " no encontrada"));

            // Actualizar solo los campos que se proporcionan en el DTO
            if (StringUtils.hasText(movieDTO.getTitle())) {
                existingMovie.setTitle(movieDTO.getTitle());
            }
            if (StringUtils.hasText(movieDTO.getDirector())) {
                existingMovie.setDirector(movieDTO.getDirector());
            }
            if (movieDTO.getReleaseYear() > 0) {
                existingMovie.setReleaseYear(movieDTO.getReleaseYear());
            }

            // Guardar la película actualizada en el repositorio
            Movie updatedMovie = movieRepository.save(existingMovie);
            return movieMapper.toDTO(updatedMovie);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateMovieException("La película con el título '" + movieDTO.getTitle() + "' ya existe.");
        }
    }

    // Eliminar una película
    public void deleteMovie(Long id) {
            // Verificar que existe la película
            Movie existingMovie = movieRepository.findById(id)
                    .orElseThrow(() -> new MovieNotFoundException("Película con ID " + id + " no encontrada"));

       this.movieRepository.delete(existingMovie);
    }

}
