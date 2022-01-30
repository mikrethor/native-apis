package com.xavierbouclet.nativeapi;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(PostgresDataResource.class)
class MovieResourceTest {

    @DisplayName("Post an movie should be correct")
    @Test
    void postAnMovie() {
        var movie = new Movie();
        movie.title = "Kung Fury 2";
        movie.year = 2022;

        var result = given()
                .body(movie)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .post("/movies")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract()
                .response()
                .jsonPath();
        assertThat(result.getString("title")).isEqualTo(movie.title);
        assertThat(result.getString("id")).isNotNull();
    }

    @DisplayName("Delete an movie should be correct")
    @Test
    void deleteAnMovie() {
        var movie = new Movie();
        movie.title = "Kung Fury";
        movie.year = 2015;

        var postResult = given()
                .body(movie)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .post("/movies")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract()
                .as(Movie.class);

        assertThat(postResult).isNotNull();
        assertThat(movie.title).isEqualTo(postResult.title);

        var deleteResult = given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .delete("/movies/" + postResult.id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract()
                .as(UUID.class);


        assertThat(deleteResult).isNotNull().isEqualTo(postResult.id);
    }

    @DisplayName("Get sould be correct")
    @Test
    void getAnMovie() {
        var movie = given()
                .when().get("/movies/a2c31765-8958-4862-9675-b5f853e6e299")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Movie.class);

        assertThat(movie).isNotNull();
        assertThat(movie.title).isEqualTo("Kung Fury");
        assertThat(movie.id).isEqualTo(UUID.fromString("a2c31765-8958-4862-9675-b5f853e6e299"));
    }
}
