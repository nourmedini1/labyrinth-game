package com.algo.application.models;


import com.algo.domain.common.Coordinates;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(name = "GetScoreRequest", description = "Request to get the score of a player")
public class GetScoreRequest {

    @Schema(description = "The id of the player", required = true)
    private String playerId;

    @Schema(description = "Indicates whether the client is the challenger or not", required = true)
    private boolean isChallenger;

    @Schema(description = "The number of retries during the game", required = true)
    @Min(0)
    @Max(5)
    private int numberOfRetries;


    @Schema(description = "The path found by the player", required = true)
    private List<Coordinates> foundPath;

    @Schema(description = "The time taken by the player to find the path in seconds", required = true)
    private int timeTakenInSeconds;

    @Schema(description = "Indicates whether the game is completed or not", required = true)
    private boolean isCompleted;


}
