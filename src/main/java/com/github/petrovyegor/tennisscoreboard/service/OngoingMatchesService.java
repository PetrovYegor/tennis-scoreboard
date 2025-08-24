package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
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
    private final MemoryOngoingMatchDao memoryOngoingMatchDao = new MemoryOngoingMatchDao();

    public NewMatchResponseDto prepareNewMatch(NewMatchRequestDto newMatchRequestDto) {
        OngoingMatch ongoingMatch = createOngoingMatch(newMatchRequestDto);
        saveOngoingMatch(ongoingMatch);
        return new NewMatchResponseDto(ongoingMatch.getUuid());
    }

    private OngoingMatch createOngoingMatch(NewMatchRequestDto newMatchRequestDto) {
        List<Player> players = getPlayers(newMatchRequestDto);
        int firstPlayerId = players.get(0).getId();
        int secondPlayerId = players.get(1).getId();
        UUID ongoingMatchId = UUID.randomUUID();
        return new OngoingMatch(ongoingMatchId, firstPlayerId, secondPlayerId);
    }

    private List<Player> getPlayers(NewMatchRequestDto newMatchRequestDto) {
        List<Player> result = new ArrayList<>();
        String firstPlayerName = newMatchRequestDto.getFirstPlayerName();
        String secondPlayerName = newMatchRequestDto.getSecondPlayerName();
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

    private OngoingMatch saveOngoingMatch(OngoingMatch ongoingMatch) {
        return memoryOngoingMatchDao.save(ongoingMatch);
    }
}
