package com.xavierbouclet.nativeapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

record MovieHandler(MovieService service) {

    public Mono<ServerResponse> list(ServerRequest request) {
        return ServerResponse.ok().body(service.list(), Movie.class);
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.get(id), Movie.class);
    }

    public Mono<ServerResponse> add(ServerRequest request) {
        return request.bodyToMono(Movie.class)
                .doOnNext(service::add)
                .flatMap(movie -> ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(service.modify(movie), Movie.class));


//        var title = movie.getTitle();
//
//        if (StringUtils.hasText(title) && ("Kung Fury" .equals(title) || "Kung Fury 2" .equals(title))) {
//            return ServerResponse.status(HttpStatus.CREATED).body(service.add(movie), Movie.class);
//        }
//        var problem = Problem.builder()
//                .withType(URI.create("https://mymovieapp.com/probs/cant-add-other-movie-kung-fury"))
//                .withTitle("Kung Fury movies are the only ones")
//                .withDetail("How dare you add this movie ? Only Kung Fury movies are worthy.")
//                .withStatus(Status.FORBIDDEN)
//                .build();
//        return ServerResponse.status(HttpStatus.FORBIDDEN).body(problem, Problem.class);
    }

    public Mono<ServerResponse> modify(ServerRequest request) {
        return request.bodyToMono(Movie.class)
                .doOnNext(service::modify)
                .flatMap(movie -> ServerResponse.accepted().contentType(MediaType.APPLICATION_JSON).body(service.modify(movie), Movie.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.delete(id), UUID.class);
    }


    public Mono<ServerResponse> addActorToMovie(ServerRequest request) {

        UUID id = UUID.fromString(request.pathVariable("id"));
        UUID actorId = UUID.fromString(request.pathVariable("actorId"));

        return ServerResponse.accepted().contentType(MediaType.APPLICATION_JSON).body(service.addActorToMovie(id, actorId), Movie.class);
    }

    public Mono<ServerResponse> deleteActorFromMovie(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        UUID actorId = UUID.fromString(request.pathVariable("actorId"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.removeActorFromMovie(id, actorId), Void.class);
    }
}
