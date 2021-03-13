package com.lafabrique.digit.owl

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("actors")
class ActorController(val service: ActorService) {

    @GetMapping
    fun list(): ResponseEntity<*> {
        val actors = service.list()
        return ResponseEntity.status(HttpStatus.OK).body(actors)
    }

    @GetMapping("/{id}")
    fun list(@PathVariable id: UUID): ResponseEntity<*> {
        val actor = service.get(id)
        return ResponseEntity.status(HttpStatus.OK).body(actor)
    }

    @PostMapping
    fun add(@RequestBody actor: Actor): ResponseEntity<*>? {
        val actor2 = service.add(actor)
        return ResponseEntity.status(HttpStatus.CREATED).body(actor2)
    }

    @PutMapping
    fun modify(actor: Actor): ResponseEntity<*>? {
        val actor2 = service.modify(actor)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(actor2)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<*>? {
        val id = service.delete(id)
        return ResponseEntity.status(HttpStatus.OK).body(id)
    }

    @PutMapping("/{id}/movies/{movieId}")
    fun addActorToMovie(@PathVariable id: UUID, @PathVariable movieId: UUID): ResponseEntity<*>? {
        val actor = service.addMovieToActor(id, movieId)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(actor)
    }

    @DeleteMapping("/{id}/movies/{movieId}")
    fun deleteActorFromMovie(@PathVariable id: UUID, @PathVariable movieId: UUID): ResponseEntity<*>? {
        val id = service.removeMovieFromActor(id, movieId)
        return ResponseEntity.status(HttpStatus.OK).body(id)
    }

}
