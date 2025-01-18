package com.algo.application.models;

import com.algo.domain.common.Coordinates;
import com.algo.domain.entities.Labyrinth;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Schema(name = "ChallengeResponse", description = "Response to a challenge")
public class ChallengeResponse {
    private String id;

    private Labyrinth initialLabyrinth;

    private int difficultyLevel;

    private List<Coordinates> shortestPath;

    private String theme;

    private LocalDateTime createdAt;
}
