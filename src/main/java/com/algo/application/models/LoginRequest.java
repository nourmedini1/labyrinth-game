package com.algo.application.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(name = "LoginRequest", description = "The request to login a player")
public class LoginRequest {
    @NotBlank
    @Schema(description = "The name of the player", required = true)
    private String name;
}
