package com.algo.application.models;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "UpdateChallengeResponse", description = "Response to a challenge update request")
public class UpdateChallengeResponse {

    @Schema(description = "The message to the player", required = true)
    private String message;
}