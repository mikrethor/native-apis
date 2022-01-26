package com.xavierbouclet.nativeapi

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.apache.http.HttpHeaders
import org.apache.http.HttpStatus
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*
import javax.ws.rs.core.MediaType

@QuarkusTest
@QuarkusTestResource(DataResource::class)
class ActorResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
          .`when`().get("/todos/hello")
          .then()
             .statusCode(200)
             .body(`is`("Hello RESTEasy"))
    }


    @Test
    fun `Get sould be correct`() {
        given()
                .`when`().get("/todos")
                .then()
                .statusCode(200)
                .body(`is`("[]"))
    }

    @Test
    fun `Post sould be correct`() {
        val todo = Actor()
        todo.fullName="Buy beer"
        todo.id=UUID.randomUUID()

        val result = given()
                .body(todo)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .`when`()
                .post("/todos")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .extract()
                .response()
                .jsonPath()
        Assertions.assertEquals("Buy beer", result.getString("title"))
        Assertions.assertEquals(false, result.getBoolean("completed"))
    }
}
