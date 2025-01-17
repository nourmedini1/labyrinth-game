package com.algo.domain.common;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Theme {
    HISTORY("HISTORY"),
    SPORTS("SPORTS"),
    GEOGRAPHY("GEOGRAPHY"),
    MOVIES("MOVIES"),
    SCIENCE("SCIENCE"),
    LITERATURE("LITERATURE"),
    MUSIC("MUSIC"),
    ART("ART"),
    POLITICS("POLITICS"),
    TECHNOLOGY("TECHNOLOGY"),
    NATURE("NATURE"),
    FOOD("FOOD"),
    ANIMALS("ANIMALS"),
    CULTURE("CULTURE"),
    RELIGION("RELIGION"),
    MYTHOLOGY("MYTHOLOGY"),
    LANGUAGE("LANGUAGE");




    private final String value;


    Theme(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }


    @JsonCreator
    public static Theme forValue(String value) {
        return Arrays.stream(Theme.values())
                .filter(op -> op.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

}