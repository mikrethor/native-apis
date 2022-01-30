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
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(PostgresDataResource.class)
class ActorResourceTest {

    @DisplayName("Post an actor should be correct")
    @Test
    void postAnActor() {
        var actor = new Actor();
        actor.fullName = "David Sandberg";

        var result = given()
                .body(actor)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .post("/actors")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract()
                .response()
                .jsonPath();
        assertThat(result.getString("fullName")).isEqualTo(actor.fullName);
        assertThat(result.getString("id")).isNotNull();
    }

    @DisplayName("Delete an actor should be correct")
    @Test
    void deleteAnActor() {
        var actor = new Actor();
        actor.fullName = "David Hasselhoff";

        var postResult = given()
                .body(actor)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .post("/actors")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract()
                .as(Actor.class);

        assertThat(postResult).isNotNull();
        assertThat(actor.fullName).isEqualTo(postResult.fullName);

        var deleteResult = given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .delete("/actors/" + postResult.id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract()
                .as(UUID.class);


        assertThat(deleteResult).isNotNull().isEqualTo(postResult.id);
    }

    @DisplayName("Get sould be correct")
    @Test
    void getAnActor() {
       var actor= given()
                .when().get("/actors/9376cfa9-b7f2-4519-82b7-8f3b9248ef24")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Actor.class);

        assertThat(actor).isNotNull();
        assertThat(actor.fullName).isEqualTo("David Sandberg");
        assertThat(actor.id).isEqualTo(UUID.fromString("9376cfa9-b7f2-4519-82b7-8f3b9248ef24"));
    }
}
