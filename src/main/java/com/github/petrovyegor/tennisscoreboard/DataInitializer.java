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

            Match match1 = new Match(player1.getId(), player2.getId(), player1.getId());
            Match match2 = new Match(player6.getId(), player5.getId(), player6.getId());
            Match match3 = new Match(player2.getId(), player3.getId(), player2.getId());
            Match match4 = new Match(player5.getId(), player4.getId(), player5.getId());
            Match match5 = new Match(player3.getId(), player4.getId(), player3.getId());
            Match match6 = new Match(player4.getId(), player3.getId(), player4.getId());
            Match match7 = new Match(player5.getId(), player5.getId(), player5.getId());
            Match match8 = new Match(player6.getId(), player1.getId(), player6.getId());
            Match match9 = new Match(player5.getId(), player2.getId(), player5.getId());
            Match match10 = new Match(player2.getId(), player1.getId(), player1.getId());
            Match match11 = new Match(player6.getId(), player4.getId(), player6.getId());
            Match match12 = new Match(player3.getId(), player5.getId(), player5.getId());

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
