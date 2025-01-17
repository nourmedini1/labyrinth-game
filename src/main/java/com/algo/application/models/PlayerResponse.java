package com.algo.application.models;


import com.algo.domain.common.annotations.ValidObjectId;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(name = "PlayerResponse", description = "Response of a player")
public class PlayerResponse {
    @ValidObjectId
    private String id;

    private String name;

    @Min(0)
    private int score;
}
