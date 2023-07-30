package com.trivago.hotelmanagement.model.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReputationBadge {
    GREEN("green"),
    YELLOW("yellow"),
    RED("red");

    private String value;
    ReputationBadge(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return  this.value;
    }
}
