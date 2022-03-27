package com.xavierbouclet.nativeapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(R2dbcConfig.class)
@ExtendWith(PostgresSQLExtension.class)
@ContextConfiguration(initializers = PostgresSQLExtension.Initializer.class)
class EndToEndTest {



    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("Post an actor should be correct")
    @Test
    void postAnActor() {
        var actor = new Actor();
        actor.setFullName("David Sandberg");

        var response = webTestClient
                .post()
                .uri("/actors")
                .bodyValue(actor)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Actor.class);

        var resultActor = response.getResponseBody().blockFirst();
        assertNotNull(resultActor);
        assertEquals(actor.getFullName(), resultActor.getFullName());
    }

    @DisplayName("Delete an actor should be correct")
    @Test
    void deleteAnActor() {
        var actor = new Actor();
        actor.setFullName("David Hasselhoff");

        var response =  webTestClient.post()
                .uri("/actors")
                .bodyValue(actor)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Actor.class);

        var resultActor = response.getResponseBody().blockFirst();
        assertNotNull(resultActor);
        assertEquals(actor.getFullName(), resultActor.getFullName());

        var response2 = webTestClient.delete()
                .uri("/actors/"+resultActor.getId())
                .exchange()
                .expectStatus().isOk()
                .returnResult(Void.class);

        assertNotNull(response2);
    }


    @DisplayName("Post a movie should be correct")
    @Test
    void postAMovie() {
        var movie = new Movie();
        movie.setTitle("Kung Fury");
        movie.setYear(2015);

        var response = webTestClient
                .post()
                .uri("/movies")
                .bodyValue(movie)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Movie.class);

        var resultMovie = response.getResponseBody().blockFirst();
        assertNotNull(resultMovie);
        assertEquals(movie.getTitle(), resultMovie.getTitle());
    }

    @DisplayName("Delete a movie should be correct")
    @Test
    void deleteAMovie() {
        var movie = new Movie();
        movie.setTitle("Kung Fury 2");
        movie.setYear(2022);


        var response = webTestClient
                .post()
                .uri("/movies")
                .bodyValue(movie)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Movie.class);

        var resultMovie = response.getResponseBody().blockFirst();
        assertNotNull(resultMovie);
        assertEquals(movie.getTitle(), resultMovie.getTitle());

        var response2 = webTestClient.delete()
                .uri("/movies/"+resultMovie.getId())
                .exchange()
                .expectStatus().isOk()
                .returnResult(Void.class);

        assertNotNull(response2);
    }

    @DisplayName("Add an actor to a movie should be correct")
    @Test
    void addAnActorToAMovie() {
        var movie = new Movie();
        movie.setTitle("Kung Fury");

        var response = webTestClient
                .post()
                .uri("/movies")
                .bodyValue(movie)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Movie.class);

        var resultMovie = response.getResponseBody().blockFirst();
        assertNotNull(resultMovie);
        assertEquals(movie.getTitle(), resultMovie.getTitle());

        var actor = new Actor();
        actor.setFullName("Jorma Taccone");

        var responseActor =  webTestClient.post()
                .uri("/actors")
                .bodyValue(actor)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Actor.class);

        var resultActor = responseActor.getResponseBody().blockFirst();

        var actorId = resultActor.getId();
        var movieId = resultMovie.getId();


        var responseActorActorInMovie =
                webTestClient.put()
                        .uri("/movies/" + movieId + "/actors/" + actorId)
                        .exchange()
                        .expectStatus().isAccepted()
                        .returnResult(ActorMovie.class);

        var resultActorMovie = responseActorActorInMovie.getResponseBody().blockFirst();

        assertNotNull(resultActorMovie);
        assertEquals(resultMovie.getId(), resultActorMovie.getMovieId());
        assertEquals(resultActor.getId(), resultActorMovie.getActorId());
    }
}
