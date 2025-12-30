package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.model.entity.Player;

import java.util.Optional;

public interface PlayerDao extends BaseEntityDao<Player, Long> {
    Optional<Player> findByName(String name);
}
