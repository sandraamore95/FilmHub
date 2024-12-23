package com.FilmHub.service;
import com.FilmHub.exceptions.MovieNotFoundException;
import com.FilmHub.exceptions.UserNotFoundException;
import com.FilmHub.feign.MovieClient;
import com.FilmHub.feign.UserClient;
import com.FilmHub.models.Favorite;
import com.FilmHub.payload.dto.FavoriteDTO;
import com.FilmHub.payload.dto.MovieDTO;
import com.FilmHub.payload.dto.UserDTO;
import com.FilmHub.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MovieClient movieClient;
    private final UserClient userClient;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository, MovieClient movieClient, UserClient userClient) {
        this.favoriteRepository = favoriteRepository;
        this.movieClient=movieClient;
        this.userClient=userClient;
    }

    //CRUD
    public List<MovieDTO> getFavoritesMoviesByUserId(Long userId) {
        // Validar si existe el usuario
        validateUser(userId);

        // Obtener favoritos del repositorio
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);

        // Extraer todos los IDs de películas
        List<Long> movieIds = favorites.stream()
                .map(Favorite::getMovieId)
                .collect(Collectors.toList());

        // Obtener todas las películas en una sola llamada
        List<MovieDTO> movies = movieClient.getMoviesByIds(movieIds);

        // Crear un mapa para búsqueda rápida
        Map<Long, MovieDTO> movieMap = movies.stream()
                .collect(Collectors.toMap(MovieDTO::getId, Function.identity()));

        // Mapear favoritos a MovieDTOs, manteniendo el orden original
        return favorites.stream()
                .map(favorite -> movieMap.get(favorite.getMovieId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public FavoriteDTO addFavorite(Long userId, Long movieId) {

        //Validar si existe el usuario
        validateUser(userId);

        //Validar si existe la movie
        validateMovie(movieId);

        // Validar duplicados
        if (favoriteRepository.existsByUserIdAndMovieId(userId, movieId)) {
            throw new IllegalArgumentException("El usuario ya tiene esta película como favorita.");
        }
        Favorite favorite = new Favorite(userId, movieId);
        Favorite savedFavorite = favoriteRepository.save(favorite);

        // Convertir la entidad a DTO
        return new FavoriteDTO(savedFavorite.getId(), savedFavorite.getUserId(), savedFavorite.getMovieId());
    }

    public FavoriteDTO updateFavorite(Long favoriteId, Long userId, Long newMovieId) {
        // Validar si el usuario existe
        validateUser(userId);

        // Validar si la nueva película existe
        validateMovie(newMovieId);

        // Verificar si el favorito con ese ID existe
        Favorite existingFavorite = favoriteRepository.findById(favoriteId).orElseThrow(() ->
                new IllegalArgumentException("El favorito con ID " + favoriteId + " no encontrado"));

        // Asegurarse de que el favorito pertenece al usuario adecuado
        if (!existingFavorite.getUserId().equals(userId)) {
            throw new IllegalArgumentException("El favorito no pertenece al usuario con ID " + userId);
        }

        // Verificar si la nueva película ya está en favoritos
        if (favoriteRepository.existsByUserIdAndMovieId(userId, newMovieId)) {
            throw new IllegalArgumentException("La nueva película ya está en favoritos");
        }
        // Actualizar el movieId del favorito
        existingFavorite.setMovieId(newMovieId);

        // Guardar el favorito actualizado
        Favorite updatedFavorite = favoriteRepository.save(existingFavorite);

        // Convertir la entidad a DTO y devolverlo
        return new FavoriteDTO(updatedFavorite.getId(), updatedFavorite.getUserId(), updatedFavorite.getMovieId());
    }






    public boolean removeFavorite(Long id) {
        if (!favoriteRepository.existsById(id)) {
            return false;
        }
        favoriteRepository.deleteById(id);
        return true;
    }



    //-----------------------------------------------------------------

    // LO HACEMOS EN UN METODO VOID -> valida y lanza excepcion!

    // Validar si el user  existe llamando al microservicio msvc-user
    private void validateUser(Long userId) {
        if (userClient.getUserById(userId) == null) {
            throw new UserNotFoundException("Usuario con ID " + userId + " no encontrado");
        }
    }
    // Validar si el user  existe llamando al microservicio msvc-movie
    private void validateMovie(Long movieId) {
        if (movieClient.getMovieById(movieId) == null) {
            throw new MovieNotFoundException("La película con ID " + movieId + " no existe");
        }
    }
}
