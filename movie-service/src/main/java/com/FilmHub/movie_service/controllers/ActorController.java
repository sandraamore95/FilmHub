package com.FilmHub.movie_service.controllers;
import com.FilmHub.movie_service.payload.dto.ActorDTO;
import com.FilmHub.movie_service.payload.dto.MovieDTO;
import com.FilmHub.movie_service.payload.request.ActorRequest;
import com.FilmHub.movie_service.service.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    // Crear actor
    @PostMapping
    public ResponseEntity<ActorDTO> createActor(@Validated @RequestBody ActorRequest actorRequest) {
        ActorDTO actorDTO = actorService.createActor(actorRequest);
        return ResponseEntity.ok(actorDTO);
    }

    // Obtener todos los actores
    @GetMapping
    public ResponseEntity<List<ActorDTO>> getAllActors() {
        List<ActorDTO> actors = actorService.getAllActors();
        return ResponseEntity.ok(actors);
    }

    // Obtener actor por ID
    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Long id) {
        ActorDTO actorDTO = actorService.getActorById(id);
        return ResponseEntity.ok(actorDTO);
    }

    // Actualizar actor
    @PutMapping("/{id}")
    public ResponseEntity<ActorDTO> updateActor(@PathVariable Long id, @Validated @RequestBody ActorRequest newActor) {
        ActorDTO updatedActor = actorService.updateActor(id, newActor);
        return ResponseEntity.ok(updatedActor);
    }

    // Añadir pelicula a actor
    @PostMapping("/{actorId}/movies")
    public ResponseEntity<?> addMoviesToActor(@PathVariable Long actorId, @RequestBody Set<Long> moviesIds) {
        ActorDTO updatedActor = actorService.addMovieToActor(actorId, moviesIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedActor);
    }

    // Eliminar actor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }
}
