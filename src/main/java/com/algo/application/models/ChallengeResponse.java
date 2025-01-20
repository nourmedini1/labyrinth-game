package com.algo.application.models;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Schema(name = "ChallengeResponse", description = "Response to a challenge")
public class ChallengeResponse {
    private String id;

    private String labyrinthId;

    private int difficultyLevel;

    private String theme;

    private LocalDateTime createdAt;
}
