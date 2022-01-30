package com.xavierbouclet.nativeapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(PostgresSQLExtension.class)
@ContextConfiguration(initializers = PostgresSQLExtension.Initializer.class)
class EndToEndTest {

    private static PostgreSQLContainer<?> postgresSQLServer =  new PostgreSQLContainer<>("postgres:14.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    static {
        postgresSQLServer.start();
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @DisplayName("Post an actor should be correct")
    @Test
    void postAnActor() {
        var actor = new Actor();
        actor.setFullName("David Sandberg");

        var headers = new HttpHeaders();
        var requestEntity = new HttpEntity<>(actor, headers);
        var response = restTemplate.exchange("/actors", HttpMethod.POST, requestEntity, Actor.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(actor.getFullName(), response.getBody().getFullName());
    }

    @DisplayName("Delete an actor should be correct")
    @Test
    void deleteAnActor() {
        var actor = new Actor();
        actor.setFullName("David Hasselhoff");

        var headers = new HttpHeaders();
        var requestEntity = new HttpEntity<>(actor, headers);
        var response = restTemplate.exchange("/actors", HttpMethod.POST, requestEntity, Actor.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(actor.getFullName(), response.getBody().getFullName());

        var response2 = restTemplate.exchange("/actors/" + response.getBody().getId(), HttpMethod.DELETE, requestEntity, String.class);

        assertNotNull(response2.getBody());
        assertEquals(response
                        .getBody()
                        .getId()
                        .toString(),
                response2
                        .getBody()
                        .substring(response2.getBody().indexOf("\"")+1,response2.getBody().lastIndexOf("\""))
        );
    }


    @DisplayName("Post a movie should be correct")
    @Test
    void postAMovie() {
        var movie = new Movie();
        movie.setTitle("Kung Fury");
        movie.setYear(2015);

        var headers = new HttpHeaders();
        var requestEntity = new HttpEntity<>(movie, headers);
        var response = restTemplate.exchange("/movies", HttpMethod.POST, requestEntity, Movie.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(movie.getTitle(), response.getBody().getTitle());
    }

    @DisplayName("Delete a movie should be correct")
    @Test
     void deleteAMovie() {
        var movie = new Movie();
        movie.setTitle("Kung Fury 2");
        movie.setYear(2022);

        var headers = new HttpHeaders();
        var requestEntity = new HttpEntity<>(movie, headers);
        var response = restTemplate.exchange("/movies", HttpMethod.POST, requestEntity, Movie.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(movie.getTitle(), response.getBody().getTitle());

        var response2 = restTemplate.exchange("/movies/" + response.getBody().getId(), HttpMethod.DELETE, requestEntity, String.class);

        assertNotNull(response2.getBody());
        assertEquals(response
                        .getBody()
                        .getId()
                        .toString(),
                response2
                        .getBody()
                        .substring(response2.getBody().indexOf("\"")+1,response2.getBody().lastIndexOf("\""))
        );
    }

    @DisplayName("Add an actor to a movie should be correct")
    @Test
    void addAnActorToAMovie() {
        var movie = new Movie();
        movie.setTitle("Kung Fury");

        var headers = new HttpHeaders();
        var requestEntity = new HttpEntity<>(movie, headers);
        var response = restTemplate.exchange("/movies", HttpMethod.POST, requestEntity, Movie.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(movie.getTitle(), response.getBody().getTitle());

        var actor = new Actor();
        actor.setFullName("Jorma Taccone");

        var headersActor = new HttpHeaders();
        var requestEntityActor = new HttpEntity<>(actor, headersActor);
        var responseActor = restTemplate.exchange("/actors", HttpMethod.POST, requestEntityActor, Actor.class);

        assertEquals(HttpStatus.CREATED, responseActor.getStatusCode());
        assertNotNull(responseActor.getBody());
        assertEquals(actor.getFullName(), responseActor.getBody().getFullName());

        var actorId = responseActor.getBody().getId();
        var movieId = response.getBody().getId();

        var headersActorInMovie = new HttpHeaders();
        var requestEntityActorInMovie = new HttpEntity<>(actor, headersActorInMovie);
        var responseActorActorInMovie = restTemplate.exchange("/movies/"+movieId+"/actors/"+actorId, HttpMethod.PUT, requestEntityActorInMovie, Movie.class);

        assertEquals(HttpStatus.ACCEPTED, responseActorActorInMovie.getStatusCode());
        assertNotNull(responseActorActorInMovie.getBody());
        assertEquals(movie.getTitle(), responseActorActorInMovie.getBody().getTitle());
        assertEquals(1, responseActorActorInMovie.getBody().getActors().size());

        var actorInMovie = responseActorActorInMovie.getBody().getActors().get(0);

        assertEquals(actorId, actorInMovie.getId());
        assertEquals(actor.getFullName(), actorInMovie.getFullName());
    }
}
