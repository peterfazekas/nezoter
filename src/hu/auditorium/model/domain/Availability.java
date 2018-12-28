package hu.auditorium.model.domain;

import java.util.Arrays;

public enum Availability {

    OCCUPIED("x"),
    AVAILABLE("o");

    private final String id;

    Availability(String id) {
        this.id = id;
    }

    public static Availability getById(String id) {
        return Arrays.stream(Availability.values()).filter(i -> i.getId().equals(id)).findAny().orElse(null);
    }

    public String getId() {
        return id;
    }
}
