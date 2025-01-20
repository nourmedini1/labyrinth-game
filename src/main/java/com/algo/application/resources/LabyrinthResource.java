package com.algo.application.resources;

import com.algo.application.services.LabyrinthService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
@Path(LabyrinthResource.CONTEXT_PATH)
public class LabyrinthResource {

    @Inject
    LabyrinthService labyrinthService;

    public static final String CONTEXT_PATH = "/labyrinth";

    @GET
    @Path("/{id}")
    public Response getLabyrinth( @PathParam("id") String id) {
        return Response.ok(labyrinthService.getLabyrinth(id)).build();
    }
}
