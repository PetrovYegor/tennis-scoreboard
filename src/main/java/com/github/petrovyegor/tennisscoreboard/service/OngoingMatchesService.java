package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.model.Match;
import com.github.petrovyegor.tennisscoreboard.model.MatchScore;

import java.util.ArrayList;
import java.util.List;

public class OngoingMatchesService {
    //private final HashMap<Integer,String> ongoingMatches = new HashMap<>();
    private final List<MatchScore> ongoingMatches = new ArrayList<>();

    public void addNewOngoingMatch(Match match) {
        ongoingMatches.add(new MatchScore(match, ""));
    }
}
