package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryOngoingMatchDao implements CrudDao<OngoingMatch, UUID> {
    private static final ConcurrentHashMap<UUID, OngoingMatch> ongoingMatches = new ConcurrentHashMap<>();

    public Optional<OngoingMatch> findById(UUID matchUuid) {//подумать, может ли прилелеть запрос на поиск ид, которого не сущесвтует. Как реагировать? Исключение?
        OngoingMatch ongoingMatch = ongoingMatches.get(matchUuid);
        return Optional.ofNullable(ongoingMatch);
    }

    public OngoingMatch save(OngoingMatch ongoingMatch) {
        ongoingMatches.put(ongoingMatch.getUuid(), ongoingMatch);
        return ongoingMatch;
    }

    public List<OngoingMatch> findAll() {
        return ongoingMatches.values().stream().toList();
    }

    public void delete(UUID matchUuid){
        ongoingMatches.remove(matchUuid);
    }

    public boolean isOngoingMatchExist(UUID matchUuid){
        return ongoingMatches.containsKey(matchUuid);
    }
}

