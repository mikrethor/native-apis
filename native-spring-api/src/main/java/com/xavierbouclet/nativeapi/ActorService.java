package com.xavierbouclet.nativeapi;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static java.util.Set.*;

public record ActorService(ActorRepository repository,
                           MovieRepository movieRepository,
                           ActorMovieRepository actorMovieRepository) {

    public Flux<Actor> list() {
        return repository.findAll().flatMap(a -> Mono.just(a).zipWith(movieRepository.findMoviesByActorId(a.getId()).collectList())
                .map(result -> {
                    result.getT1().setMovies(copyOf(result.getT2()));
                    return result.getT1();
                }));
    }

    public Mono<Actor> add(Actor actor) {
        return repository.save(actor);
    }

    public Mono<Actor> modify(Actor actor) {
        return repository.save(actor);
    }

    public Mono<UUID> delete(UUID id) {
        return repository.deleteById(id).map(v -> id);
    }

    public Mono<Actor> get(UUID id) {
        return repository.findById(id).zipWith(movieRepository.findMoviesByActorId(id).collectList())
                .map(result -> {
                    result.getT1().setMovies(copyOf(result.getT2()));
                    return result.getT1();
                });
    }

    public Mono<ActorMovie> addMovieToActor(UUID id, UUID idMovie) {
        return actorMovieRepository.save(new ActorMovie(id, idMovie));
    }

    public Mono<Void> removeMovieFromActor(UUID id, UUID idMovie) {
        return actorMovieRepository.deleteByMovieIdAndActorId(idMovie, id);
    }

}
