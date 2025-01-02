package com.FilmHub.movie_service.repository;
import com.FilmHub.movie_service.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitleIgnoreCase(String title);
    Set<Movie> findAllById(Set<Long> ids);

}