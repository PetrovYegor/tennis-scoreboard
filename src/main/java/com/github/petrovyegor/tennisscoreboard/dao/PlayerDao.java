package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.JpaUtil;
import com.github.petrovyegor.tennisscoreboard.exception.DBException;
import com.github.petrovyegor.tennisscoreboard.model.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Optional;

public class PlayerDao {
    public Optional<Player> findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Player result = em.find(Player.class, id);
            return Optional.ofNullable(result);
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
            throw new DBException("Failed to save Player with name '%s'".formatted(player.getName()));
        } finally {
            em.close();
        }
    }
}