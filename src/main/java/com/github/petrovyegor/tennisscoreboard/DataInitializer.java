package com.github.petrovyegor.tennisscoreboard;

import com.github.petrovyegor.tennisscoreboard.exception.DBException;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;
import com.github.petrovyegor.tennisscoreboard.model.entity.Match;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DataInitializer {
    public static void initTestData(){
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Player player1 = new Player("John");
            Player player2 = new Player("William");
            Player player3 = new Player("Robert");
            Player player4 = new Player("Julia");
            Player player5 = new Player("Arnold");
            Player player6 = new Player("Marry");

            em.persist(player1);
            em.persist(player2);
            em.persist(player3);
            em.persist(player4);
            em.persist(player5);
            em.persist(player6);

            Match match1 = new Match(player1, player2, player1);
            Match match2 = new Match(player6, player5, player6);
            Match match3 = new Match(player2, player3, player2);
            Match match4 = new Match(player5, player4, player5);
            Match match5 = new Match(player3, player4, player3);
            Match match6 = new Match(player4, player3, player4);
            Match match7 = new Match(player5, player5, player5);
            Match match8 = new Match(player6, player1, player6);
            Match match9 = new Match(player5, player2, player5);
            Match match10 = new Match(player2, player1, player1);
            Match match11 = new Match(player6, player4, player6);
            Match match12 = new Match(player3, player5, player5);
            Match match13 = new Match(player6, player4, player6);
            Match match14 = new Match(player6, player4, player6);
            Match match15 = new Match(player6, player4, player6);
            Match match16 = new Match(player6, player4, player6);
            Match match17 = new Match(player6, player4, player6);
            Match match18 = new Match(player6, player4, player6);
            Match match19 = new Match(player6, player4, player6);
            Match match20 = new Match(player6, player4, player6);

            em.persist(match1);
            em.persist(match2);
            em.persist(match3);
            em.persist(match4);
            em.persist(match5);
            em.persist(match6);
            em.persist(match7);
            em.persist(match8);
            em.persist(match9);
            em.persist(match10);
            em.persist(match11);
            em.persist(match12);
            em.persist(match13);
            em.persist(match14);
            em.persist(match15);
            em.persist(match16);
            em.persist(match17);
            em.persist(match18);
            em.persist(match19);
            em.persist(match20);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new DBException("Failed to initialize DB with test data. " + e);
        } finally {
            em.close();
        }
    }
}
