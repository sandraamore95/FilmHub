package com.FilmHub.movie_service.repository;
import com.FilmHub.movie_service.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
