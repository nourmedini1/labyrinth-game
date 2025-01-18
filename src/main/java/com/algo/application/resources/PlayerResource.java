package com.algo.application.resources;

import com.algo.application.models.CreatePlayerRequest;
import com.algo.application.models.LoginRequest;
import com.algo.application.models.PlayerResponse;
import com.algo.application.models.UpdatePlayerRequest;
import com.algo.application.services.PlayerService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(PlayerResource.CONTEXT_PATH)
@Slf4j
public class PlayerResource {

    public static final String CONTEXT_PATH = "/players";

    @Inject
    PlayerService playerService;


    @POST
    @Path("/sign-up")
    public Response signUp(CreatePlayerRequest createPlayerRequest) {
        PlayerResponse playerResponse = playerService.createPlayer(createPlayerRequest);
        return Response.ok(playerResponse).status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/sign-in")
    public Response signIn(LoginRequest loginRequest) {
        PlayerResponse playerResponse = playerService.getPlayerByName(loginRequest.getName());
        return Response.ok(playerResponse).status(Response.Status.OK).build();
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updatePlayer(@PathParam("id") String id, UpdatePlayerRequest updatePlayerRequest) {
        PlayerResponse playerResponse = playerService.updatePlayer(id,updatePlayerRequest);
        return Response.ok(playerResponse).status(Response.Status.OK).build();
    }

    @GET
    @Path("/{id}")
    public Response getPlayer(@PathParam("id") String id) {
        PlayerResponse playerResponse = playerService.getPlayer(id);
        return Response.ok(playerResponse).status(Response.Status.OK).build();
    }

    @GET
    @Path("/name/{name}")
    public Response getPlayerByName(@PathParam("name") String name) {
        PlayerResponse playerResponse = playerService.getPlayerByName(name);
        return Response.ok(playerResponse).status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deletePlayer(@PathParam("id") String id) {
        playerService.deletePlayer(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/sorted")
    public Response getPlayersSortedByScore(@QueryParam("page") int page, @QueryParam("size") int size) {
        return Response.ok(playerService.getPlayersSortedByScore(page, size)).status(Response.Status.OK).build();
    }
}
