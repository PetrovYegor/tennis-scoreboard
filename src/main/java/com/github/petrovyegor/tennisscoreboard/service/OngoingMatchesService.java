package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.model.Match;

import java.util.HashMap;

public class OngoingMatchesService {
    private final HashMap<Integer,String> ongoingMatches = new HashMap<>();

    public void addNewOngoingMatch(Match match){
        ongoingMatches.put(match.getId(), "");
    }
}
