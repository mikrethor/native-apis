package com.xavierbouclet.nativeapi;

import org.zalando.problem.Status;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/actors")
public class ActorResource {

    private final ActorService service;

    @Inject
    public ActorResource(ActorService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Actor> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Actor get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    public Response add(Actor actor) {
        return Response.status(Status.CREATED.getStatusCode()).entity(service.add(actor)).build();
    }

    @PUT
    public Response modify(Actor actor) {
        return Response.accepted(service.modify(actor)).
                build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        return Response.ok(service.delete(id)).
                build();
    }

    @PUT
    @Path("/{id}/movies/{movieId}")
    public Response addMovieToActor(@PathParam("id") UUID id, @PathParam("id") UUID movieId) {
        return Response.accepted(service.addMovieToActor(id, movieId)).
                build();
    }

    @DELETE
    @Path("/{id}/movies/{movieId}")
    public Response deleteMovieFromActor(@PathParam("id") UUID id, @PathParam("movieId") UUID movieId) {
        return Response.ok(service.removeMovieFromActor(id, movieId)).
                build();
    }
}
