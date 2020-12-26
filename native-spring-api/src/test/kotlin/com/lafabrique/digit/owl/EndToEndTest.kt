package com.lafabrique.digit.owl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(PostgresSQLExtension::class)
@ContextConfiguration(initializers = [PostgresSQLExtension.Initializer::class])
class EndToEndTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `Post should be correct`() {
        val todo = Todo()
        todo.id = UUID.randomUUID()
        todo.completed = false
        todo.title = "Buy beers"

        val headers = HttpHeaders()
        val requestEntity = HttpEntity(todo, headers)
        val response = restTemplate.exchange("/todos", HttpMethod.POST, requestEntity, Todo::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(todo.title, response.body?.title)
        assertEquals(todo.completed, response.body?.completed)
    }

    @Test
    fun `Delete should be correct`() {
        val todo = Todo()
        todo.completed = false
        todo.title = "Buy beers"

        val headers = HttpHeaders()
        val requestEntity = HttpEntity(todo, headers)
        val response = restTemplate.exchange("/todos", HttpMethod.POST, requestEntity, Todo::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(todo.title, response.body?.title)
        assertEquals(todo.completed, response.body?.completed)

        val response2 = restTemplate.exchange("/todos/"+response.body?.id, HttpMethod.DELETE, requestEntity, String::class.java)

        assertNotNull(response2.body)
        assertEquals(response
                .body
                ?.id
                .toString(),
                response2
                        .body
                        ?.removePrefix("\"")
                        ?.removeSuffix("\""))
    }
}
