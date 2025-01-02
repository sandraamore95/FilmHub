package com.FilmHub.movie_service.controllers;

import com.FilmHub.movie_service.payload.dto.MovieDTO;
import com.FilmHub.movie_service.payload.request.MovieRequest;
import com.FilmHub.movie_service.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

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

    // Obtener película por titulo -> /movies/"inception"
    @GetMapping("/{title}")
    public ResponseEntity<?> getMovieByTitle(@PathVariable String  title) {
        // Excepciones posibles que pueden ser lanzadas:
        // MovieNotFoundException si no se encuentra la película
        MovieDTO movie = movieService.getMovieByTitle(title);
        return ResponseEntity.ok(movie);
    }


    // Crear nueva película
    @PostMapping
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieRequest newMovie) {
        // Excepciones posibles que pueden ser lanzadas:
        // DuplicateMovieException si ya existe una película con el mismo título
        MovieDTO savedMovie = movieService.createMovie(newMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    // Actualizar pelicula
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody MovieRequest newMovie) {
        // Excepciones posibles que pueden ser lanzadas:
        // MovieNotFoundException si no se encuentra la película con el ID especificado
        // DuplicateMovieException si ya existe una película con el mismo título
        MovieDTO updatedMovie = movieService.updateMovie(id, newMovie);
        return ResponseEntity.ok(updatedMovie);
    }

    // Añadir actor a pelicula
    @PostMapping("/{movieId}/actors")
    public ResponseEntity<?> addActorsToMovie(@PathVariable Long movieId, @RequestBody Set<Long> actorIds) {
        MovieDTO updatedMovie = movieService.addActorsToMovie(movieId, actorIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedMovie);
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
