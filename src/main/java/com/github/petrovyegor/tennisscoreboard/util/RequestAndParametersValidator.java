package com.github.petrovyegor.tennisscoreboard.util;

import com.github.petrovyegor.tennisscoreboard.exception.ErrorMessage;
import com.github.petrovyegor.tennisscoreboard.exception.InvalidRequestException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Set;

public class RequestAndParametersValidator {
    public static void validateNewMatchPostRequest(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        Set<String> requiredParameters = Set.of("player1_name", "player2_name");
        boolean isValid = parameters.keySet().containsAll(requiredParameters);
        if (!isValid) {
            request.setAttribute("error", "One or both names are empty");
            throw new InvalidRequestException(ErrorMessage.INVALID_NEW_MATCH_POST_REQUEST);
        }
    }
}
