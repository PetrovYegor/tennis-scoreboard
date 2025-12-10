package com.github.petrovyegor.tennisscoreboard;

import com.github.petrovyegor.tennisscoreboard.model.entity.Match;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataInitializer {
    private static final String[] PLAYER_NAMES = {"John", "William", "Robert", "Julia", "Arnold", "Marry", "Harry", "Bryan"};

    public static void initTestData() {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();//TODO не нравится, переделать весь класс

        try {
            transaction.begin();
            Player[] players = new Player[PLAYER_NAMES.length];
            for (int i = 0; i < players.length; i++) {
                Player newPlayer = new Player(PLAYER_NAMES[i]);
                players[i] = newPlayer;
                em.persist(newPlayer);
            }
            createMatch(em, players[0], players[1], players[0]); //John vs William, John wins
            createMatch(em, players[1], players[2], players[1]); //William vs Robert, William wins
            createMatch(em, players[2], players[3], players[2]); //Robert vs Julia, Robert wins
            createMatch(em, players[3], players[4], players[3]); //Julia vs Arnold, Julia wins
            createMatch(em, players[4], players[5], players[4]); //Arnold vs Marry, Arnold wins
            createMatch(em, players[5], players[6], players[5]); //Marry vs Harry, Marry wins
            createMatch(em, players[6], players[7], players[6]); //Harry vs Bryan, Harry wins
            createMatch(em, players[7], players[0], players[7]); //Bryan vs John, Bryan wins
            createMatch(em, players[0], players[1], players[0]); //John vs William, John wins
            createMatch(em, players[1], players[2], players[1]); //William vs Robert, William wins
            createMatch(em, players[2], players[3], players[2]); //Robert vs Julia, Robert wins
            createMatch(em, players[3], players[4], players[3]); //Julia vs Arnold, Julia wins
            createMatch(em, players[4], players[5], players[4]); //Arnold vs Marry, Arnold wins
            createMatch(em, players[5], players[6], players[5]); //Marry vs Harry, Marry wins
            createMatch(em, players[6], players[7], players[6]); //Harry vs Bryan, Harry wins
            createMatch(em, players[7], players[0], players[7]); //Bryan vs John, Bryan wins
            createMatch(em, players[0], players[1], players[0]); //John vs William, John wins
            createMatch(em, players[1], players[2], players[1]); //William vs Robert, William wins
            createMatch(em, players[2], players[3], players[2]); //Robert vs Julia, Robert wins
            createMatch(em, players[3], players[4], players[3]); //Julia vs Arnold, Julia wins
            createMatch(em, players[4], players[5], players[4]); //Arnold vs Marry, Arnold wins

            transaction.commit();
        } catch (Exception e) {
            log.error("Failed to initialize DB with test data. " + e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            em.close();
        }
    }

    private static void createMatch(EntityManager em, Player firstPlayer, Player secondPlayer, Player winner) {
        Match match = new Match(firstPlayer, secondPlayer, winner);
        em.persist(match);
    }
}

