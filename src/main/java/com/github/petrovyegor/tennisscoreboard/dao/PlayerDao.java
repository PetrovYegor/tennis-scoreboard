package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.JpaUtil;
import com.github.petrovyegor.tennisscoreboard.exception.DBException;
import com.github.petrovyegor.tennisscoreboard.model.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDao {
    public List<Player> findAll() {
        List<Player> result = new ArrayList<>();
        String findAllQuery = "SELECT p FROM Player p";
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Query query = em.createQuery(findAllQuery, Player.class);
            //result = query.getResultList();
            return query.getResultList();//может вернуть null
        } catch (Throwable e) {
            throw new DBException("Failed to get all players");
        }
    }

    public Optional<Player> findById(int id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Player result = em.find(Player.class, id);
            return Optional.ofNullable(result);
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