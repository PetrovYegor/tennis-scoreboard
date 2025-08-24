package com.github.petrovyegor.tennisscoreboard.exception;

public class RestErrorException extends RuntimeException {
    public RestErrorException(String message) {
        super(message);
    }
}
