package com.algo.domain.entities;

import com.algo.domain.common.Coordinates;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "labyrinths")
public class Labyrinth extends PanacheMongoEntityBase implements Serializable {
    @BsonId
    private  String id;

    @BsonProperty("nodes")
    private Node[][] nodes;

    @BsonProperty("start")
    private Coordinates start;

    @BsonProperty("end")
    private Coordinates end;

    @BsonProperty("width")
    private int width;

    @BsonProperty("height")
    private int height;

}
