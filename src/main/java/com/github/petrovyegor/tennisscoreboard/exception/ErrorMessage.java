package com.github.petrovyegor.tennisscoreboard.exception;

public class ErrorMessage {
    public static final String INVALID_NEW_MATCH_POST_REQUEST = "One or both names are missing";
    public static final String ONGOING_MATCH_NOT_FOUND_BY_UUID = "Ongoing match with uuid '%s' does not exist!";
}
