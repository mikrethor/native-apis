package com.lafabrique.digit.owl


import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import java.util.*

@Controller
class TodoController(val service: TodoService) {

    @Get(value = "/todos", produces = [MediaType.APPLICATION_JSON])
    fun list(): HttpResponse<*> {
        return HttpResponse.status<Any>(HttpStatus.OK).body<Any>(service.list())
    }

    @Get(value = "/todos/{title}", produces = [MediaType.APPLICATION_JSON])
    fun list(@PathVariable title: String): HttpResponse<*> {
        return HttpResponse.status<Any>(HttpStatus.OK).body<Any>(service.list(title))
    }

    @Post("/todos", produces = [MediaType.APPLICATION_JSON])
    fun add(@Body todo: Todo): HttpResponse<*>? {
        return HttpResponse.status<Any>(HttpStatus.CREATED).body<Any>(service.add(todo))
    }

    @Put("/todos", produces = [MediaType.APPLICATION_JSON])
    fun modify(todo: Todo): HttpResponse<*>? {
        return HttpResponse.status<Any>(HttpStatus.ACCEPTED).body<Any>(service.modify(todo))
    }

    @Delete("/todos/{id}", produces = [MediaType.APPLICATION_JSON])
    fun delete(@PathVariable id: UUID): HttpResponse<*>? {
        return HttpResponse.status<Any>(HttpStatus.ACCEPTED).body<Any>(service.delete(id))
    }
}
