package com.xavierbouclet.nativeapi;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.util.Set.copyOf;

record MovieService(
        MovieRepository repository,
        ActorRepository actorRepository,
        ActorMovieRepository actorMovieRepository
) {

    public Flux<Movie> list() {
        return repository.findAll().flatMap(movie -> Mono.just(movie).zipWith(actorRepository.findActorsByMovieId(movie.getId()).collectList())
                .map(result -> {
                    result.getT1().setActors(copyOf(result.getT2()));
                    return result.getT1();
                }));
    }

    public Mono<Movie> add(Movie movie) {
        return repository.save(movie);
    }

    public Mono<Movie> modify(Movie movie) {
        return repository.save(movie);
    }

    public Mono<UUID> delete(UUID id) {
        return repository.deleteById(id).map(v -> id);
    }

    public Mono<Movie> get(UUID id) {
        return repository.findById(id).zipWith(actorRepository.findActorsByMovieId(id).collectList())
                .map(result -> {
                    result.getT1().setActors(copyOf(result.getT2()));
                    return result.getT1();
                });
    }

    public Mono<ActorMovie> addActorToMovie(UUID id, UUID actorId) {
        return actorMovieRepository.save(new ActorMovie(actorId,id));
    }

    public Mono<Void> removeActorFromMovie(UUID id, UUID actorId) {
        return actorMovieRepository.deleteByMovieIdAndActorId(id, actorId);
    }

}
