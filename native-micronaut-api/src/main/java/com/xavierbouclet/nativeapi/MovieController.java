package com.xavierbouclet.nativeapi;


import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import java.net.URI;
import java.util.*;

@Controller(value = "/movies")
record MovieController(MovieService service) {

    @Get
    HttpResponse list() {
        return HttpResponse.status(HttpStatus.OK).body(service.list());
    }

    @Get("/{id}")
    HttpResponse list(@PathVariable UUID id) {
        return HttpResponse.status(HttpStatus.OK).body(service.get(id));
    }

    @Post
    HttpResponse add(@Body Movie movie) {
        var title = movie.getTitle();

        if (StringUtils.hasText(title) && ("Kung Fury" == title || "Kung Fury 2" == title)) {
            return HttpResponse.status(HttpStatus.CREATED).body(service.add(movie));
        }
        var problem = Problem.builder()
                .withType(URI.create("https://mymovieapp.com/probs/cant-add-other-movie-kung-fury"))
                .withTitle("Kung Fury movies are the only ones")
                .withDetail("How dare you add this movie ? Only Kung Fury movies are worthy.")
                .withStatus(Status.FORBIDDEN)
                .build();
        return HttpResponse.status(HttpStatus.FORBIDDEN).body(problem);
    }

    @Put
    HttpResponse modify(Movie movie) {
        return HttpResponse.status(HttpStatus.ACCEPTED).body(service.modify(movie));
    }

    @Delete("/{id}")
    HttpResponse delete(@PathVariable UUID id) {
        return HttpResponse.status(HttpStatus.OK).body(service.delete(id));
    }

    @Put("/{id}/actors/{actorId}")
    HttpResponse addActorToMovie(@PathVariable UUID id, @PathVariable UUID actorId ) {
        var movie = service.addActorToMovie(id, actorId);
        return HttpResponse.status(HttpStatus.ACCEPTED).body(movie);
    }

    @Delete("/{id}/actors/{actorId}")
    HttpResponse deleteActorFromMovie(@PathVariable UUID id, @PathVariable UUID actorId ) {
        return HttpResponse.status(HttpStatus.OK).body(service.removeActorFromMovie(id, actorId));
    }
}
