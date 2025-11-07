package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.model.entity.Player;

import java.util.Optional;

public interface PlayerDao extends CrudDao<Player, Long> {
    Optional<Player> findById(Long id);

    Optional<Player> findByName(String name);
}
