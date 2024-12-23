package com.FilmHub.movie_service.repository;
import com.FilmHub.movie_service.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findByTitle(String title);
}