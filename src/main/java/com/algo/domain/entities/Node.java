package com.algo.domain.entities;


import com.algo.domain.common.Coordinates;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "nodes")
public class Node extends PanacheMongoEntityBase implements Serializable {
    @BsonProperty("coordinates")
    private Coordinates coordinates;

    @BsonProperty("is_wall")
    private boolean isWall;

    @BsonProperty("value")
    private char value;

    @BsonProperty("neighbors")
    private List<Coordinates> neighbors;




}
