package com.xavierbouclet.nativeapi

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("actors")
class ActorController(val service: ActorService) {

    @GetMapping
    fun list(): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.OK).body(service.list())
    }

    @GetMapping("/{id}")
    fun list(@PathVariable id: UUID): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.OK).body(service.get(id))
    }

    @PostMapping
    fun add(@RequestBody actor: Actor): ResponseEntity<*>? {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(actor))
    }

    @PutMapping
    fun modify(actor: Actor): ResponseEntity<*>? {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.modify(actor))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<*>? {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id))
    }

    @PutMapping("/{id}/movies/{movieId}")
    fun addActorToMovie(@PathVariable id: UUID, @PathVariable movieId: UUID): ResponseEntity<*>? {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.addMovieToActor(id, movieId))
    }

    @DeleteMapping("/{id}/movies/{movieId}")
    fun deleteActorFromMovie(@PathVariable id: UUID, @PathVariable movieId: UUID): ResponseEntity<*>? {
        return ResponseEntity.status(HttpStatus.OK).body(service.removeMovieFromActor(id, movieId))
    }

}
