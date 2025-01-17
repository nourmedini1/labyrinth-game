package com.algo.application.models;


import com.algo.domain.common.annotations.ValidObjectId;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(name = "CreateChallengeRequest", description = "Data needed to create a challenge")
public class CreateChallengeRequest {
    @Schema(required = true)
    @ValidObjectId
    private String challengerId;

    @Schema(required = true)
    @ValidObjectId
    private String challengedId;

    @Schema(required = true)
    @Min(1)
    @Max(3)
    private int difficultyLevel;

    @Schema(required = true)
    private String theme;
}
