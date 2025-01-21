package com.algo.domain.entities;


import com.algo.domain.common.Coordinates;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node extends PanacheMongoEntityBase {
    @BsonProperty("coordinates")
    private Coordinates coordinates;

    @BsonProperty("is_wall")
    private boolean isWall;

    @BsonProperty("value")
    private char value;

    @BsonProperty("neighbors")
    private List<Coordinates> neighbors;




}
