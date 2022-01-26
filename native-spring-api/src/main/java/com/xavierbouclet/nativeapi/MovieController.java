package com.xavierbouclet.nativeapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("movies")
record MovieController(MovieService service) {

    @GetMapping
    public ResponseEntity<List<Movie>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> get(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.get(id));
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Movie movie) {
        var title = movie.getTitle();

        if (StringUtils.hasText(title) && ("Kung Fury".equals(title) || "Kung Fury 2".equals(title))) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.add(movie));
        }
        var problem = Problem.builder()
                .withType(URI.create("https://mymovieapp.com/probs/cant-add-other-movie-kung-fury"))
                .withTitle("Kung Fury movies are the only ones")
                .withDetail("How dare you add this movie ? Only Kung Fury movies are worthy.")
                .withStatus(Status.FORBIDDEN)
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problem);
    }

    @PutMapping
    public ResponseEntity<Movie> modify(Movie movie) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.modify(movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> delete(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

    @PutMapping("/{id}/actors/{actorId}")
    public ResponseEntity<Movie> addActorToMovie(@PathVariable UUID id, @PathVariable UUID actorId) {
        var movie = service.addActorToMovie(id, actorId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(movie);
    }

    @DeleteMapping("/{id}/actors/{actorId}")
    public ResponseEntity<Movie> deleteActorFromMovie(@PathVariable UUID id, @PathVariable UUID actorId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.removeActorFromMovie(id, actorId));
    }
}
