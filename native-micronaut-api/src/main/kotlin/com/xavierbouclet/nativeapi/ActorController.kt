package com.xavierbouclet.nativeapi

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import java.util.*

@Controller(value = "/actors")
class ActorController(val service: ActorService) {

    @Get
    fun list(): HttpResponse<*> {
        return HttpResponse.status<Any>(HttpStatus.OK).body(service.list())
    }

    @Get("/{id}")
    fun list(@PathVariable id: UUID): HttpResponse<*> {
        return HttpResponse.status<Any>(HttpStatus.OK).body(service.get(id))
    }

    @Post
    fun add(@Body actor: Actor): HttpResponse<*>? {
        return HttpResponse.status<Any>(HttpStatus.CREATED).body(service.add(actor))
    }

    @Put
    fun modify(actor: Actor): HttpResponse<*>? {
        return HttpResponse.status<Any>(HttpStatus.ACCEPTED).body(service.modify(actor))
    }

    @Delete("/{id}")
    fun delete(@PathVariable id: UUID): HttpResponse<*>? {
        return HttpResponse.status<Any>(HttpStatus.OK).body(service.delete(id))
    }

    @Put("/{id}/movies/{movieId}")
    fun addActorToMovie(@PathVariable id: UUID, @PathVariable movieId: UUID): HttpResponse<*>? {
        return HttpResponse.status<Any>(HttpStatus.ACCEPTED).body(service.addMovieToActor(id, movieId))
    }

    @Delete("/{id}/movies/{movieId}")
    fun deleteActorFromMovie(@PathVariable id: UUID, @PathVariable movieId: UUID): HttpResponse<*>? {
        return HttpResponse.status<Any>(HttpStatus.OK).body(service.removeMovieFromActor(id, movieId))
    }

}
