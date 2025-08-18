package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchDao implements CrudDao<OngoingMatch, UUID> {
    private final ConcurrentHashMap<UUID, OngoingMatch> ongoingMatches;

    public OngoingMatchDao() {
        ongoingMatches = new ConcurrentHashMap<>();
    }

    public Optional<OngoingMatch> findById(UUID id) {//подумать, может ли прилелеть запрос на поиск ид, которого не сущесвтует. Как реагировать? Исключение?
        OngoingMatch ongoingMatch = ongoingMatches.get(id);
        return Optional.ofNullable(ongoingMatch);
    }

    public OngoingMatch save(OngoingMatch ongoingMatch) {
        ongoingMatches.put(ongoingMatch.getUuid(), ongoingMatch);
        return ongoingMatch;
    }

    public List<OngoingMatch> findAll() {
        return ongoingMatches.values().stream().toList();
    }

}

