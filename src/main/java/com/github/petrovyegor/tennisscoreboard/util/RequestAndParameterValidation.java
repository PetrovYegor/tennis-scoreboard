package com.github.petrovyegor.tennisscoreboard.util;

import com.github.petrovyegor.tennisscoreboard.exception.InvalidParamException;
import com.github.petrovyegor.tennisscoreboard.exception.InvalidRequestException;
import com.github.petrovyegor.tennisscoreboard.exception.RestErrorException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RequestAndParameterValidation {
    private static final String FILTER_BY_NAME_PATTERN = "[a-zA-Z0-9\\W_]{0,50}";

    public static boolean isNullOrEmpty(String source) {
        return source == null || source.trim().isEmpty();
    }

    public static boolean isNameValid(String name) {
        return name.matches(FILTER_BY_NAME_PATTERN);
    }

    public static boolean isPageNotNullAndValid(String pageParameter) {
        return !isNullOrEmpty(pageParameter) && isPageParameterValid(pageParameter);
    }

    public static boolean isPageParameterMoreThenTotalPages(int pageFromRequest, int totalPages) {
        return (pageFromRequest > totalPages) && (totalPages > 0);
    }

    public static boolean isUUIDValid(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isRoundWinnerIdValid(String winnerIdParameter){
        try {
            Integer.parseInt(winnerIdParameter);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static void validateNewMatchPostRequestParameters(String firstPlayerName, String secondPlayerName) {
        ensureNamesNotNullAndNotEmpty(firstPlayerName, secondPlayerName);
        ensureNamesAreDifferent(firstPlayerName, secondPlayerName);
        validatePlayerNameLength(firstPlayerName);
        validatePlayerNameLength(secondPlayerName);
    }

    public static void validateNewMatchPostRequest(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        Set<String> requiredParameters = Set.of("player1_name", "player2_name");
        boolean isValid = parameters.keySet().containsAll(requiredParameters);
        if (!isValid) {
            throw new InvalidRequestException("First player name or second player name are missing");
        }
    }

    private static void ensureNamesNotNullAndNotEmpty(String firstPlayerName, String secondPlayerName) {
        if (isNullOrEmpty(firstPlayerName) || isNullOrEmpty(secondPlayerName)) {
            throw new InvalidParamException("Player names must not be null or empty.");
        }
    }

    private static void ensureNamesAreDifferent(String firstPlayerName, String secondPlayerName) {
        String name1 = firstPlayerName.trim();
        String name2 = secondPlayerName.trim();
        if (name1.equalsIgnoreCase(name2)) {
            throw new RestErrorException("Players' names must not be the same.");
        }
    }

    private static void validatePlayerNameLength(String name) {
        if (!isNameValid(name)) {
            throw new InvalidParamException("The name length must not exceed 50 characters");
        }
    }

    private static boolean isPageParameterValid(String value) {
        try {
            int page = Integer.parseInt(value);
            return page >= 1;
        } catch (Exception e) {
            return false;
        }
    }
}
