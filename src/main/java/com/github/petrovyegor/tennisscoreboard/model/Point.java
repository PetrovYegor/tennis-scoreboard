package com.github.petrovyegor.tennisscoreboard.model;

public enum Point {
    LOVE("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    ADVANTAGE("AD"),
    GAME("GAME");

    private final String value;

    Point(String value) {
        this.value = value;
    }
}
