package com.FilmHub.repository;
import com.FilmHub.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);

    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
}
