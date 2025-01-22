package com.algo.application.resources;


import com.algo.application.models.*;
import com.algo.application.services.ChallengeService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path(ChallengeResource.CONTEXT_PATH)
@Slf4j
public class ChallengeResource {

    public static final String CONTEXT_PATH = "/challenges";

    @Inject
    ChallengeService challengeService;

    @POST
    @Path("/create")
    public Response createChallenge(CreateChallengeRequest createChallengeRequest) {
        ChallengeResponse challengeResponse;
        try {
            challengeResponse = challengeService.createChallenge(createChallengeRequest);
        } catch (Exception e) {
            log.error("Error creating challenge", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(challengeResponse).status(Response.Status.CREATED).build();
    }

    @GET
    public Response getChallenges(
            @QueryParam("page") int page, @QueryParam("size") int size,
            @QueryParam("challengerId") String challengerId,
            @QueryParam("challengedId") String challengedId,
            @QueryParam("status") String status, @QueryParam("difficultyLevel") int difficultyLevel,
            @QueryParam("winnerId") String winnerId, @QueryParam("theme") String theme) {
        return Response.ok(challengeService.getChallenges(
                page, size, challengerId, challengedId, status,
                difficultyLevel, winnerId, theme))
                .status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteChallenge(@PathParam("id") String id) {
        challengeService.deleteChallenge(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/{id}/accept")
    public Response acceptChallenge(@PathParam("id") String id) {
        ChallengeResponse challengeResponse = challengeService.acceptChallenge(id);
        return Response.ok(challengeResponse).status(Response.Status.OK).build();
    }

    @PATCH
    @Path("/{id}/decline")
    public Response declineChallenge(@PathParam("id") String id) {
        challengeService.declineChallenge(id);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{id}/update")
    public Response updateChallenge(@PathParam("id") String id , UpdateChallengeRequest getScoreRequest) {
        UpdateChallengeResponse response = challengeService.updateChallenge(id,getScoreRequest);
        return Response.ok(response).build();
    }








}
