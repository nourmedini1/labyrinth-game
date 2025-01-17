package com.algo.domain.common;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "coordinates")
public class Coordinates extends PanacheMongoEntityBase implements Serializable {
    @BsonProperty("x")
    private int x;

    @BsonProperty("y")
    private int y;
}
