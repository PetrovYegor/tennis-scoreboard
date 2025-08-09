package com.github.petrovyegor.tennisscoreboard.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

public class RequestAndParametersValidator {
    public static void validateNewMatchPostRequest(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        Set<String> requiredParameters = Set.of("player1_name", "player2_name");
        boolean isValid = parameters.keySet().containsAll(requiredParameters);
        if (!isValid) {
            throw new InvalidRequestException(SC_BAD_REQUEST, ErrorMessage.INVALID_CURRENCY_POST_REQUEST);
        }
    }
}
