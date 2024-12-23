package com.FilmHub.feign;
import com.FilmHub.payload.dto.MovieDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "msvc-movie", url = "localhost:8082")// Nombre registrado en Eureka
public interface MovieClient {

    @GetMapping("/api/movies/{id}")  // Obtener una película por ID
    MovieDTO getMovieById(@PathVariable("id") Long id);

    @GetMapping("/api/movies")  // Obtener todas las películas
    List<MovieDTO> getAllMovies();

    @GetMapping("/api/movies/moviesByIds")  // Obtener varias películas por IDs
    List<MovieDTO> getMoviesByIds(@RequestParam("ids") List<Long> ids);
}


/*
Este cliente actúa como un "cliente remoto" para consumir los endpoints expuestos
por el microservicio de Movies, pero los métodos de esos endpoints
deben estar implementados en el controlador de Movies, en el Feing  sin el ResponseEntity.

 */