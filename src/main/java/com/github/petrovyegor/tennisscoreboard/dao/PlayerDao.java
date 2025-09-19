package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.model.entity.Player;

import java.util.Optional;

public interface PlayerDao extends CrudDao<Player, Integer> {
    Optional<Player> findByName(String name);
}
