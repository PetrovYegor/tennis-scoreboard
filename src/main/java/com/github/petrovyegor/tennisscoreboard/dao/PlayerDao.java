package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.JpaUtil;
import com.github.petrovyegor.tennisscoreboard.model.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PlayerDao {
    public Player findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Player.class, id);
        } finally {
            em.close();
        }
    }

    public void save(Player player) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}