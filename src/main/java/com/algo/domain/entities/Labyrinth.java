package com.algo.domain.entities;

import com.algo.domain.common.Coordinates;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "labyrinths")
@Schema(name = "Labyrinth", description = "A labyrinth related to a specific challenge")
public class Labyrinth extends PanacheMongoEntityBase {
    @BsonId
    private ObjectId id;

    @BsonProperty("nodes")
    private List<List<Node>> nodes;

    @BsonProperty("start")
    private Coordinates start;

    @BsonProperty("end")
    private Coordinates end;

    @BsonProperty("width")
    private int width;

    @BsonProperty("height")
    private int height;

    private List<Coordinates> shortestPath;

}
