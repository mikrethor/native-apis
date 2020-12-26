package com.lafabrique.digit.owl

import io.netty.handler.codec.http.HttpResponseStatus
import org.jboss.resteasy.annotations.Body
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TodoResource(@Inject val service: TodoService) {

    @GET
    @Path("hello")
    fun hello() = "Hello RESTEasy"

    @GET
    fun list(): MutableIterable<Todo> {
        return service.list()
    }

    @GET
    @Path("{title}")
    fun list(@PathParam("title") title: String): List<Todo> {
        return service.list(title)
    }

    @POST
    fun add(todo: Todo): Response? {
        return  Response.ok(service.add(todo)).status(HttpResponseStatus.CREATED.code()).build();
    }

    @PUT
    fun modify(todo: Todo): Todo {
        return service.modify(todo)
    }

    @DELETE
    @Path("{id}")
    fun delete(@PathParam("id")  id: UUID): UUID {
        return service.delete(id)
    }
}
