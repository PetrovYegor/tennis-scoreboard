package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dao.PlayerDao;
import com.github.petrovyegor.tennisscoreboard.exception.NotFoundException;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;

import java.util.Optional;

public class PlayerService {
    private final JpaPlayerDao jpaPlayerDao;

    public PlayerService() {
        this.jpaPlayerDao = new JpaPlayerDao();
    }

    public Player getOrCreatePlayer(String playerName) {
        Optional<Player> player = jpaPlayerDao.findByName(playerName);
        if (player.isEmpty()) {
            return savePlayer(new Player(playerName));
        }
        return player.get();
    }

    private Player savePlayer(Player player) {
        return jpaPlayerDao.save(player);
    }

    public String getPlayerName(int id){
        Player player = jpaPlayerDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Player nor found"));
        return player.getName();
    }
}
