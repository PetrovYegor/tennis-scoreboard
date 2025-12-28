package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.model.match.OngoingMatch;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryOngoingMatchDao implements CrudDao<OngoingMatch, UUID> {
    private static final ConcurrentHashMap<UUID, OngoingMatch> ongoingMatches = new ConcurrentHashMap<>();

    public Optional<OngoingMatch> findById(UUID matchUuid) {
        OngoingMatch ongoingMatch = ongoingMatches.get(matchUuid);
        return Optional.ofNullable(ongoingMatch);
    }

    public OngoingMatch save(OngoingMatch ongoingMatch) {
        ongoingMatches.put(ongoingMatch.getUuid(), ongoingMatch);
        return ongoingMatch;
    }

    public void delete(UUID matchUuid) {
        ongoingMatches.remove(matchUuid);
    }

    public boolean isOngoingMatchExist(UUID matchUuid) {
        return ongoingMatches.containsKey(matchUuid);
    }
}

