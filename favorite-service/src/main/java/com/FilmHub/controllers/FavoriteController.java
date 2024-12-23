package com.FilmHub.controllers;
import com.FilmHub.models.Favorite;
import com.FilmHub.payload.dto.FavoriteDTO;
import com.FilmHub.payload.dto.MovieDTO;
import com.FilmHub.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // Obtener las peliculas favoritas de un usuario
    @GetMapping("/{userId}")
    public ResponseEntity<List<MovieDTO>> getFavoritesMoviesByUserId(@PathVariable Long userId) {
        try {
            List<MovieDTO> favoriteMovies = favoriteService.getFavoritesMoviesByUserId(userId);
            return ResponseEntity.ok(favoriteMovies);
        } catch (IllegalArgumentException e) {
            // Manejar errores de validación del usuario
            return ResponseEntity.badRequest().body(Collections.emptyList());//si esta vacio
        } catch (Exception e) {
            //500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    @PostMapping
    public ResponseEntity<FavoriteDTO> addFavorite(@RequestParam Long userId, @RequestParam Long movieId) {
        FavoriteDTO favorite = favoriteService.addFavorite(userId, movieId);
        return new ResponseEntity<>(favorite, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long id) {
        boolean removed = favoriteService.removeFavorite(id);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
