package com.xavierbouclet.nativeapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

record ActorHandler(ActorService service) {

    public Mono<ServerResponse> list(ServerRequest request) {
        return ServerResponse.ok().body(service.list(), Actor.class);
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.get(id), Actor.class);
    }

    //    @PostMapping
    public Mono<ServerResponse> add(ServerRequest request) {
        return request.bodyToMono(Actor.class)
                .doOnNext(service::add)
                .flatMap(actor -> ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(service.modify(actor), Actor.class));
    }

    public Mono<ServerResponse> modify(ServerRequest request) {
        return request.bodyToMono(Actor.class)
                .doOnNext(service::modify)
                .flatMap(actor -> ServerResponse.accepted().contentType(MediaType.APPLICATION_JSON).body(service.modify(actor), Actor.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.delete(id), UUID.class);
    }

    public Mono<ServerResponse> addActorToMovie(ServerRequest request) {

        UUID id = UUID.fromString(request.pathVariable("id"));
        UUID movieId = UUID.fromString(request.pathVariable("movieId"));

        return ServerResponse.accepted().contentType(MediaType.APPLICATION_JSON).body(service.addMovieToActor(id, movieId), Actor.class);
    }

    public Mono<ServerResponse> deleteActorFromMovie(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        UUID movieId = UUID.fromString(request.pathVariable("movieId"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.removeMovieFromActor(id, movieId), Void.class);
    }

}
