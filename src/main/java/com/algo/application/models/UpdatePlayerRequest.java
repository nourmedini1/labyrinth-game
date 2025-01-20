package com.algo.application.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.reactive.RestForm;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(name = "UpdatePlayerRequest", description = "The request to update a player")
public class UpdatePlayerRequest {

    @RestForm
    @Schema(description = "The name of the player", required = true)
    private String name;

    @RestForm
    @Schema(description = "The score of the player", required = true)
    private int score;
}
