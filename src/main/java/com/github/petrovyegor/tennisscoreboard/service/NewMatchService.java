package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dto.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.model.Match;
import com.github.petrovyegor.tennisscoreboard.model.Player;

public class NewMatchService {

    private final JpaPlayerDao jpaPlayerDao = new JpaPlayerDao();
    //Получить дто+
    //вытащить из него имена+
    //проверить, что по заданным именнам существуют игроки+
    // если кого-то из игроков не существует - создать их+
    // создать матч
    //сохранить матч в HashMap, где ключ - ид матча,

    void processNewMatch(NewMatchRequestDto newMatchRequestDto) {//поменять название метода
        String firstPlayerName = newMatchRequestDto.firstPlayerName();
        String secondPlayerName = newMatchRequestDto.secondPlayerName();

        if (!jpaPlayerDao.findByName(firstPlayerName).isPresent()){//убрать дублирование
            jpaPlayerDao.save(new Player(firstPlayerName));
        }
        if (!jpaPlayerDao.findByName(secondPlayerName).isPresent()){
            jpaPlayerDao.save(new Player(secondPlayerName));
        }

        Match match = new Match();
    }
}
