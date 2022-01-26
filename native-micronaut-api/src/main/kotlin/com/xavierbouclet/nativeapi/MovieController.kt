package com.xavierbouclet.nativeapi


import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import org.zalando.problem.Problem
import org.zalando.problem.Status
import java.net.URI
import java.util.*

@Controller(value = "/movies")
class MovieController(val service: MovieService) {

    @Get
    fun list(): HttpResponse<*> {
        return HttpResponse.status<Any>(HttpStatus.OK).body(service.list())
    }

    @Get("/{id}")
    fun list(@PathVariable id: UUID): HttpResponse<*> {
        return HttpResponse.status<Any>(HttpStatus.OK).body(service.get(id))
    }

    @Post
    fun add(@Body movie: Movie): HttpResponse<*>? {
        val title = movie.title

        if (title.isNotBlank() && ("Kung Fury" == title || "Kung Fury 2" == title)) {
            return HttpResponse.status<Any>(HttpStatus.CREATED).body(service.add(movie))
        }
        val problem = Problem.builder()
                .withType(URI.create("https://mymovieapp.com/probs/cant-add-other-movie-kung-fury"))
                .withTitle("Kung Fury movies are the only ones")
                .withDetail("How dare you add this movie ? Only Kung Fury movies are worthy.")
                .withStatus(Status.FORBIDDEN)
                .build()
        return HttpResponse.status<Any>(HttpStatus.FORBIDDEN).body(problem)
    }

    @Put
    fun modify(movie: Movie): HttpResponse<*>? {
        return HttpResponse.status<Any>(HttpStatus.ACCEPTED).body(service.modify(movie))
    }

    @Delete("/{id}")
    fun delete(@PathVariable id: UUID): HttpResponse<*>? {
        return HttpResponse.status<Any>(HttpStatus.OK).body(service.delete(id))
    }

    @Put("/{id}/actors/{actorId}")
    fun addActorToMovie(@PathVariable id: UUID, @PathVariable actorId: UUID): HttpResponse<*>? {
        val movie = service.addActorToMovie(id, actorId)
        return HttpResponse.status<Any>(HttpStatus.ACCEPTED).body(movie)
    }

    @Delete("/{id}/actors/{actorId}")
    fun deleteActorFromMovie(@PathVariable id: UUID, @PathVariable actorId: UUID): HttpResponse<*>? {
        val id = service.removeActorFromMovie(id, actorId)
        return HttpResponse.status<Any>(HttpStatus.OK).body(id)
    }
}
