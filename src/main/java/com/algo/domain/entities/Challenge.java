package com.algo.domain.entities;

import com.algo.domain.common.ChallengeStatus;
import com.algo.domain.common.Coordinates;
import com.algo.domain.common.Theme;
import com.algo.domain.common.annotations.ValidObjectId;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@MongoEntity(collection = "challenges")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Challenge", description = "A challenge between two players")
public class Challenge extends PanacheMongoEntityBase implements Serializable {
    @BsonId
    private ObjectId id;

    @BsonProperty("challenger_id")
    @ValidObjectId
    private String challengerId;

    @BsonProperty("challenged_id")
    @ValidObjectId
    private String challengedId;

    @BsonProperty("challenger_score")
    @Min(0)
    @Schema(description = "The score of the challenger", required = true, defaultValue = "0")
    private int ChallengerScore;

    @BsonProperty("challenged_score")
    @Min(0)
    @Schema(description = "The score of the challenged", required = true, defaultValue = "0")
    private int ChallengedScore;

    @BsonProperty("status")
    @Schema(description = "The status of the challenge", required = true)
    private ChallengeStatus status;

    @BsonProperty("created_at")
    @PastOrPresent
    @Schema(description = "The date and time the challenge was created", required = true)
    private LocalDateTime createdAt;

    @BsonProperty("initial_labyrinth")
    @Schema(description = "The initial labyrinth of the challenge", required = true)
    private Labyrinth initialLabyrinth;

    @BsonProperty("shortest_path")
    @Schema(description = "The shortest path of the challenge", required = true)
    private List<Coordinates> shortestPath;

    @BsonProperty("difficulty_level")
    @Min(1)
    @Schema(description = "The difficulty level of the challenge", required = true)
    private int difficultyLevel;

    @BsonProperty("winner_id")
    @ValidObjectId
    @Schema(nullable = true)
    private String winnerId;

    @BsonProperty("theme")
    @Schema(description = "The theme of the challenge", required = true)
    private Theme theme;

}
