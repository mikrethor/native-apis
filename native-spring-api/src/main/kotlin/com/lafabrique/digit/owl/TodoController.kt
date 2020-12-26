package com.lafabrique.digit.owl

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class TodoController(val service: TodoService) {

    @GetMapping("/todos")
    fun list(): ResponseEntity<*> {
        val todos = service.list()
        return ResponseEntity.status(HttpStatus.OK).body(todos)
    }

    @GetMapping("/todos/{title}")
    fun list(@PathVariable title: String): ResponseEntity<*> {
        val todos = service.list(title)
        return ResponseEntity.status(HttpStatus.OK).body(todos)
    }

    @PostMapping("/todos")
    fun add(@RequestBody todo: Todo): ResponseEntity<*>? {
        val todo2 = service.add(todo)
        return ResponseEntity.status(HttpStatus.CREATED).body(todo2)
    }

    @PutMapping("/todos")
    fun modify(todo: Todo): ResponseEntity<*>? {
        val todo2 = service.modify(todo)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(todo2)
    }

    @DeleteMapping("/todos/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<*>? {
        val id = service.delete(id)
        return ResponseEntity.status(HttpStatus.OK).body(id)

    }
}
