package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dao.OngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.NewMatchResponseDto;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OngoingMatchesService {
    private final JpaPlayerDao jpaPlayerDao = new JpaPlayerDao();
    private final OngoingMatchDao ongoingMatchDao = new OngoingMatchDao();

    public NewMatchResponseDto prepareNewMatch(NewMatchRequestDto newMatchRequestDto) {
        OngoingMatch ongoingMatch = createOngoingMatch(newMatchRequestDto);
        UUID tempMatchId = UUID.randomUUID();
        saveOngoingMatch(tempMatchId, ongoingMatch);
        return new NewMatchResponseDto(tempMatchId);
    }

    private OngoingMatch createOngoingMatch(NewMatchRequestDto newMatchRequestDto) {
        List<Player> players = getPlayers(newMatchRequestDto);
        int firstPlayerId = players.get(0).getId();
        int secondPlayerId = players.get(1).getId();
        return new OngoingMatch(firstPlayerId, secondPlayerId);
    }

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
            return savePlayer(new Player(playerName));
        }
        return player.get();
    }

    private Player savePlayer(Player player) {
        return jpaPlayerDao.save(player);
    }

    private OngoingMatch saveOngoingMatch(UUID id, OngoingMatch ongoingMatch) {
        return ongoingMatchDao.save(id, ongoingMatch);
    }
}
