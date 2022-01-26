package com.xavierbouclet.nativeapi

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
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(PostgresSQLExtension::class)
@ContextConfiguration(initializers = [PostgresSQLExtension.Initializer::class])
class EndToEndTest(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun `Post an actor should be correct`() {
        val actor = Actor()
        actor.id = UUID.randomUUID()
        actor.fullName = "David Sandberg"

        val headers = HttpHeaders()
        val requestEntity = HttpEntity(actor, headers)
        val response = restTemplate.exchange("/actors", HttpMethod.POST, requestEntity, Actor::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(actor.fullName, response.body?.fullName)
    }

    @Test
    fun `Delete an actor should be correct`() {
        val actor = Actor()
        actor.id = UUID.randomUUID()
        actor.fullName = "David Hasselhoff"

        val headers = HttpHeaders()
        val requestEntity = HttpEntity(actor, headers)
        val response = restTemplate.exchange("/actors", HttpMethod.POST, requestEntity, Actor::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(actor.fullName, response.body?.fullName)

        val response2 = restTemplate.exchange("/actors/" + response.body?.id, HttpMethod.DELETE, requestEntity, String::class.java)

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


    @Test
    fun `Post a movie should be correct`() {
        val movie = Movie()
        movie.id = UUID.randomUUID()
        movie.title = "Kung Fury"
        movie.year = 2015

        val headers = HttpHeaders()
        val requestEntity = HttpEntity(movie, headers)
        val response = restTemplate.exchange("/movies", HttpMethod.POST, requestEntity, Movie::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(movie.title, response.body?.title)
    }

    @Test
    fun `Delete a movie should be correct`() {
        val movie = Movie()
        movie.title = "Kung Fury 2"
        movie.year = 2020

        val headers = HttpHeaders()
        val requestEntity = HttpEntity(movie, headers)
        val response = restTemplate.exchange("/movies", HttpMethod.POST, requestEntity, Movie::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(movie.title, response.body?.title)

        val response2 = restTemplate.exchange("/movies/" + response.body?.id, HttpMethod.DELETE, requestEntity, String::class.java)

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

    @Test
    fun `Add an actor to a movie should be correct`() {
        val movie = Movie()
        movie.title = "Kung Fury"

        val headers = HttpHeaders()
        val requestEntity = HttpEntity(movie, headers)
        val response = restTemplate.exchange("/movies", HttpMethod.POST, requestEntity, Movie::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(movie.title, response.body?.title)

        val actor = Actor()
        actor.fullName = "Jorma Taccone"

        val headersActor = HttpHeaders()
        val requestEntityActor = HttpEntity(actor, headersActor)
        val responseActor = restTemplate.exchange("/actors", HttpMethod.POST, requestEntityActor, Actor::class.java)

        assertEquals(HttpStatus.CREATED, responseActor.statusCode)
        assertNotNull(responseActor.body)
        assertEquals(actor.fullName, responseActor.body?.fullName)

        val actorId = responseActor.body?.id
        val movieId = response.body?.id


        val headersActorInMovie = HttpHeaders()
        val requestEntityActorInMovie = HttpEntity(actor, headersActorInMovie)
        val responseActorActorInMovie = restTemplate.exchange("/movies/$movieId/actors/$actorId", HttpMethod.PUT, requestEntityActorInMovie, Movie::class.java)

        assertEquals(HttpStatus.ACCEPTED, responseActorActorInMovie.statusCode)
        assertNotNull(responseActorActorInMovie.body)
        assertEquals(movie.title, responseActorActorInMovie.body?.title)
        assertEquals(1, responseActorActorInMovie.body?.actors?.size)

        val actorInMovie = responseActorActorInMovie.body?.actors?.elementAt(0)

        assertEquals(actorId, actorInMovie?.id)
        assertEquals(actor.fullName, actorInMovie?.fullName)
    }
}
