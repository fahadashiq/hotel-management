package com.trivago.hotelmanagement.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemCategory {
    HOTEL ("hotel"),
    ALTERNATIVE("alternative"),
    HOSTEL("hostel"),
    LODGE("lodge"),
    RESORT("resort"),
    GUEST_HOUSE("guest-house");

    private String value;
    ItemCategory(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return  this.value;
    }
}
