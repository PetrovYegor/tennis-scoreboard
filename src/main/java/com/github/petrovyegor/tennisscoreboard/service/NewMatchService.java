package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dto.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.model.Match;
import com.github.petrovyegor.tennisscoreboard.model.Player;

import java.util.Optional;

public class NewMatchService {
    private final JpaPlayerDao jpaPlayerDao = new JpaPlayerDao();

    public Match getNewMatch(NewMatchRequestDto newMatchRequestDto) {
        String firstPlayerName = newMatchRequestDto.firstPlayerName();
        String secondPlayerName = newMatchRequestDto.secondPlayerName();
        Player firstPlayer = getOrCreateIfNotExists(firstPlayerName);
        Player secondPlayer = getOrCreateIfNotExists(secondPlayerName);
        return new Match(firstPlayer.getId(), secondPlayer.getId());
    }

    private Player getOrCreateIfNotExists(String playerName) {//Протестировать
        Optional<Player> player = jpaPlayerDao.findByName(playerName);
        if (player.isEmpty()) {
            return save(new Player(playerName));
        }
        return player.get();
    }

    private Player save(Player player) {
        return jpaPlayerDao.save(player);
    }
}
