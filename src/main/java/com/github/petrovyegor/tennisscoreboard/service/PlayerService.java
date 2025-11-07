package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.exception.NotFoundException;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;

import java.util.Optional;

public class PlayerService {
    private final JpaPlayerDao jpaPlayerDao;

    public PlayerService() {
        this.jpaPlayerDao = new JpaPlayerDao();
    }//TODO так ли дожен инициализироваться репозиторий?

    public Player getOrCreatePlayer(String playerName) {
        Optional<Player> player = jpaPlayerDao.findByName(playerName);
        if (player.isPresent()) {
            return player.get();
        } else {
            Player newPlayer = new Player(playerName);
            return jpaPlayerDao.save(newPlayer);
        }
    }

//    public String getPlayerName(long id) { //мне не нравится этот метод, он используется только в одном месте и мб получится обойтись без него
//        Player player = jpaPlayerDao.findById(id)
//                .orElseThrow(() -> new NotFoundException("Player nor found"));
//        return player.getName();
//    }
}
