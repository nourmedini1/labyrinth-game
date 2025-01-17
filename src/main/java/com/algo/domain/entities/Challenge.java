package com.algo.domain.entities;

import com.algo.domain.common.ChallengeStatus;
import com.algo.domain.common.Theme;
import com.algo.domain.common.annotations.ValidObjectId;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @BsonProperty("status")
    private ChallengeStatus status;

    @BsonProperty("created_at")
    @PastOrPresent
    private LocalDateTime createdAt;

    @BsonProperty("initial_labyrinth")
    private Labyrinth initialLabyrinth;

    @BsonProperty("difficulty_level")
    private int difficultyLevel;

    @BsonProperty("winner_id")
    @ValidObjectId
    @Schema(nullable = true)
    private String winnerId;

    @BsonProperty("theme")
    private Theme theme;

}
