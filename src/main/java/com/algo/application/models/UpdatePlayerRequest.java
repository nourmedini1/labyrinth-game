package com.algo.application.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(name = "UpdatePlayerRequest", description = "The request to update a player")
public class UpdatePlayerRequest {
    @NotBlank
    @Schema(description = "The name of the player", required = true)
    private String name;
}
