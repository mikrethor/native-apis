package com.xavierbouclet.nativeapi

import org.zalando.problem.Status
import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("actors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ActorResource(val service: ActorService) {

    @GET
    fun list() = Response.ok(service.list()).build()

    @GET
    @Path("/{id}")
    fun list(@PathParam("id") id: UUID) = Response.ok(service.get(id)).build()

    @POST
    fun add(actor: Actor) = Response.status(Status.CREATED.statusCode).entity(service.add(actor)).build()


    @PUT
    fun modify(actor: Actor) = Response.accepted(service.modify(actor)).build()


    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: UUID) = Response.ok(service.delete(id)).build()

    @PUT
    @Path("/{id}/movies/{movieId}")
    fun addMovieToActor(@PathParam("id") id: UUID, @PathParam("id") movieId: UUID) = Response.accepted(service.addMovieToActor(id, movieId)).build()


    @DELETE
    @Path("/{id}/movies/{movieId}")
    fun deleteMovieFromActor(@PathParam("id") id: UUID, @PathParam("id") movieId: UUID) =
        Response.ok(service.removeMovieFromActor(id, movieId)).build()

}
