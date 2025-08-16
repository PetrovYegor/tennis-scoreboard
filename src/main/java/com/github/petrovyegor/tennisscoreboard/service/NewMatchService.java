package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dto.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewMatchService {
    private final JpaPlayerDao jpaPlayerDao = new JpaPlayerDao();

    private List<Player> getPlayers(NewMatchRequestDto newMatchRequestDto) {
        List<Player> result = new ArrayList<>();
        String firstPlayerName = newMatchRequestDto.firstPlayerName();
        String secondPlayerName = newMatchRequestDto.secondPlayerName();
        result.add(getOrCreateIfNotExists(firstPlayerName));
        result.add(getOrCreateIfNotExists(secondPlayerName));
        return result;
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

    public OngoingMatch getMatch(NewMatchRequestDto newMatchRequestDto) {
        List<Player> players = getPlayers(newMatchRequestDto);
        int firstPlayerId = players.get(0).getId();
        int secondPlayerId = players.get(1).getId();
        return new OngoingMatch(firstPlayerId, secondPlayerId);
    }
}
