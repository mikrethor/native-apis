package com.xavierbouclet.nativeapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("actors")
public class ActorController {

    private final ActorService service;

    public ActorController(ActorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Actor>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> get(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.get(id));
    }

    @PostMapping
    public ResponseEntity<Actor> add(@RequestBody Actor actor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(actor));
    }

    @PutMapping
    public ResponseEntity<Actor> modify(Actor actor) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.modify(actor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> delete(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

    @PutMapping("/{id}/movies/{movieId}")
    public ResponseEntity addActorToMovie(@PathVariable UUID id, @PathVariable UUID movieId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.addMovieToActor(id, movieId));
    }

    @DeleteMapping("/{id}/movies/{movieId}")
    public ResponseEntity deleteActorFromMovie(@PathVariable UUID id, @PathVariable UUID movieId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.removeMovieFromActor(id, movieId));
    }

}
