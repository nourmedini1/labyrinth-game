package com.algo.domain.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
@MongoEntity(collection = "players")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Player", description = "A player in the game")
public class Player extends PanacheMongoEntityBase implements Serializable {
    @BsonProperty("id")
    private String id;

    @BsonProperty("name")
    private String name;

    @BsonProperty("score")
    @Min(0)
    private int score;


}
