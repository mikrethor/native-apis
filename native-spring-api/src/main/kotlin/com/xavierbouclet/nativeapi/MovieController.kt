package com.xavierbouclet.nativeapi

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("movies")
class MovieController(val service: MovieService) {

    @GetMapping
    fun list(): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.OK).body(service.list())
    }

    @GetMapping("/{id}")
    fun list(@PathVariable id: UUID): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.OK).body(service.get(id))
    }

    @PostMapping
    fun add(@RequestBody movie: Movie): ResponseEntity<*>? {
        val title = movie.title

        if (title.isNotBlank() && ("Kung Fury" == title || "Kung Fury 2" == title)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.add(movie))
        }
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "How dare you add this movie ? Only Kung Fury movies are worthy.")
        problem.title = "Kung Fury movies are the only ones"
        problem.detail = "How dare you add this movie ? Only Kung Fury movies are worthy."
        problem.type = URI.create("https://mymovieapp.com/probs/cant-add-other-movie-kung-fury")

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problem)
    }

    @PutMapping
    fun modify(movie: Movie): ResponseEntity<*>? {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.modify(movie))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<*>? {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id))
    }

    @PutMapping("/{id}/actors/{actorId}")
    fun addActorToMovie(@PathVariable id: UUID, @PathVariable actorId: UUID): ResponseEntity<*>? {
        val movie = service.addActorToMovie(id, actorId)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(movie)
    }

    @DeleteMapping("/{id}/actors/{actorId}")
    fun deleteActorFromMovie(@PathVariable id: UUID, @PathVariable actorId: UUID): ResponseEntity<*>? {
        val id = service.removeActorFromMovie(id, actorId)
        return ResponseEntity.status(HttpStatus.OK).body(id)
    }
}
