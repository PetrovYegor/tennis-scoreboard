package com.github.petrovyegor.tennisscoreboard.util;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class RequestAndParameterValidation {
    private static final String FILTER_BY_NAME_PATTERN = "[\\w\\W]{0,50}";;

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

    public static boolean isRoundWinnerIdValid(String winnerIdParameter) {
        try {
            Integer.parseInt(winnerIdParameter);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean areNamesEquals(String firstPlayerName, String secondPlayerName) {
        String name1 = firstPlayerName.trim();
        String name2 = secondPlayerName.trim();
        return name1.equalsIgnoreCase(name2);
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
