package com.FilmHub.movie_service.controllers;

import com.FilmHub.movie_service.exceptions.DuplicateMovieException;
import com.FilmHub.movie_service.exceptions.MovieNotFoundException;
import com.FilmHub.movie_service.payload.MovieDTO;
import com.FilmHub.movie_service.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated  // Activa la validación de beans en el controlador
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Obtener todas las películas
    @GetMapping("/")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    // Obtener película por ID -> /movies/2
    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        // Excepciones posibles que pueden ser lanzadas:
        // MovieNotFoundException si no se encuentra la película con el ID especificado
        MovieDTO movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    // Crear nueva película
    @PostMapping
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        // Excepciones posibles que pueden ser lanzadas:
        // DuplicateMovieException si ya existe una película con el mismo título
        MovieDTO savedMovie = movieService.createMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    // Actualizar película
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        // Excepciones posibles que pueden ser lanzadas:
        // MovieNotFoundException si no se encuentra la película con el ID especificado
        // DuplicateMovieException si ya existe una película con el mismo título
        MovieDTO updatedMovie = movieService.updateMovie(id, movieDTO);
        return ResponseEntity.ok(updatedMovie);
    }

    // Eliminar película
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        // Excepciones posibles que pueden ser lanzadas:
        // MovieNotFoundException si no se encuentra la película con el ID especificado
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
