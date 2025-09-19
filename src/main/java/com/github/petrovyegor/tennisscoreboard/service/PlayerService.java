package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dao.PlayerDao;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;

import java.util.Optional;

public class PlayerService {
    private final PlayerDao jpaPlayerDao;

    public PlayerService(){
        this.jpaPlayerDao = new JpaPlayerDao();
    }

    public Player getOrCreatePlayer(String playerName) {//Протестировать
        Optional<Player> player = jpaPlayerDao.findByName(playerName);
        if (player.isEmpty()) {
            return savePlayer(new Player(playerName));
        }
        return player.get();
    }

    private Player savePlayer(Player player) {
        return jpaPlayerDao.save(player);
    }
}
