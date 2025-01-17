package com.algo.application.models;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "ScoreResponse", description = "Response to a score request")
public class ScoreResponse {

    @Schema(description = "The score of the player", required = true)
    private int score;

    @Schema(description = "The message to the player", required = true)
    private String message;
}
