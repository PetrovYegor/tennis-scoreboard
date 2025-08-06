package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.JpaUtil;
import com.github.petrovyegor.tennisscoreboard.exception.DBException;
import com.github.petrovyegor.tennisscoreboard.model.Match;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaMatchDao implements CrudDao<Match, Integer> {
    public Optional<Match> findById(Integer id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Match result = em.find(Match.class, id);
            return Optional.ofNullable(result);
        }
    }

    public List<Match> findAll() {
        List<Match> result = new ArrayList<>();
        String findAllQuery = "SELECT m FROM Match m";
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Query query = em.createQuery(findAllQuery, Match.class);
            //result = query.getResultList();
            return query.getResultList();//может вернуть null
        } catch (Throwable e) {
            throw new DBException("Failed to get all matches");
        }
    }

    public Match save(Match entity) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new DBException("Failed to save Match with player1 id - '%s', player2 id - '%s', winner id - '%s'".formatted(entity.getFirstPlayerId(), entity.getSecondPlayerId(), entity.getWinnerId()));
        } finally {
            em.close();
        }
    }
}
