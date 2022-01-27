package com.xavierbouclet.nativeapi

import org.zalando.problem.Problem
import org.zalando.problem.Status
import java.net.URI
import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class MovieResource(val service: MovieService) {

    @GET
    fun list() = Response.ok(service.list()).build()

    @GET
    @Path("/{id}")
    fun list(@PathParam("id") id: UUID) = Response.ok(service.get(id)).build()


    @POST
    fun add(movie: Movie): Response {
        val title = movie.title

        if (title.isNotBlank() && ("Kung Fury" == title || "Kung Fury 2" == title)) {
            return Response.status(Status.CREATED.statusCode).entity(service.add(movie)).build()
        }
        val problem = Problem.builder()
            .withType(URI.create("https://mymovieapp.com/probs/cant-add-other-movie-kung-fury"))
            .withTitle("Kung Fury movies are the only ones")
            .withDetail("How dare you add this movie ? Only Kung Fury movies are worthy.")
            .withStatus(Status.FORBIDDEN)
            .build()
        return Response.status(Status.FORBIDDEN.statusCode).entity(problem).build()
    }

    @PUT
    fun modify(movie: Movie): Response {
        return Response.accepted(service.modify(movie)).build()
    }

    @DELETE
    @Path("/{id}")
    fun delete(
        @PathParam("id") id: UUID
    ): Response {
        return Response.ok(service.delete(id)).build()
    }

    @PUT
    @Path("/{id}/actors/{actorId}")
    fun addActorToMovie(@PathParam("id") id: UUID, @PathParam("actorId") actorId: UUID): Response {
        return Response.accepted(service.addActorToMovie(id, actorId)).build()
    }

    @DELETE
    @Path("/{id}/actors/{actorId}")
    fun deleteActorFromMovie(@PathParam("id") id: UUID, @PathParam("actorId") actorId: UUID): Response {
        return Response.ok(service.removeActorFromMovie(id, actorId)).build()
    }
}
