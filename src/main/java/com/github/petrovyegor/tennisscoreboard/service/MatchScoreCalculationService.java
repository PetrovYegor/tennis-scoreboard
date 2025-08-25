package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;

public class MatchScoreCalculationService {
    //обновляет счёт в матче. Реализует логику подсчёта счёта матча по очкам/геймам/сетам
    //имя, количество сетов, количество геймов, текущее начение Point
    private final JpaPlayerDao jpaPlayerDao = new JpaPlayerDao();

    public void addPoint(int playerId) {

    }

}
