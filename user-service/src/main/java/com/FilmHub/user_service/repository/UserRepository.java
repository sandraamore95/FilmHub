package com.FilmHub.user_service.repository;
import com.FilmHub.user_service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
