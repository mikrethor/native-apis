package com.xavierbouclet.nativeapi;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import java.util.*;

@Controller(value = "/actors")
record ActorController(ActorService service) {

    @Get
    public HttpResponse<Iterable<Actor>> list()  {
        return HttpResponse.status(HttpStatus.OK).body(service.list());
    }

    @Get("/{id}")
     public HttpResponse<Actor> get(@PathVariable UUID id ) {
        return HttpResponse.status(HttpStatus.OK).body(service.get(id));
    }

    @Post
     public HttpResponse<Actor> add(@Body Actor actor ) {
        return HttpResponse.status(HttpStatus.CREATED).body(service.add(actor));
    }

    @Put
     public HttpResponse<Actor> modify(@Body Actor actor) {
        return HttpResponse.status(HttpStatus.ACCEPTED).body(service.modify(actor));
    }

    @Delete("/{id}")
     public HttpResponse<UUID> delete(@PathVariable UUID id ) {
        return HttpResponse.status(HttpStatus.OK).body(service.delete(id));
    }

    @Put("/{id}/movies/{movieId}")
     public HttpResponse<Actor> addActorToMovie(@PathVariable UUID id , @PathVariable UUID movieId) {
        return HttpResponse.status(HttpStatus.ACCEPTED).body(service.addMovieToActor(id, movieId));
    }

    @Delete("/{id}/movies/{movieId}")
     public HttpResponse<Actor> deleteActorFromMovie(@PathVariable UUID id , @PathVariable UUID movieId){
        return HttpResponse.status(HttpStatus.OK).body(service.removeMovieFromActor(id, movieId));
    }

}
