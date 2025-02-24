package com.algo.domain.common;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ChallengeStatus {
    ACCEPTED("ACCEPTED"),
    DECLINED("DECLINED"),
    PENDING("PENDING"),
    EXPIRED("EXPIRED"),
    FINISHED("FINISHED");



    private final String value;


    ChallengeStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }



    @JsonCreator
    public static ChallengeStatus forValue(String value) {
        return Arrays.stream(ChallengeStatus.values())
                .filter(op -> op.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

}