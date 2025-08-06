package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.JpaUtil;
import com.github.petrovyegor.tennisscoreboard.exception.DBException;
import com.github.petrovyegor.tennisscoreboard.model.Match;
import com.github.petrovyegor.tennisscoreboard.model.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchDao {
    public Optional<Match> findById(int id) {
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

    public void save(Match match) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(match);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new DBException("Failed to save Match with player1 id - '%s', player2 id - '%s', winner id - '%s'".formatted(match.getFirstPlayerId(), match.getSecondPlayerId(), match.getWinnerId()));
        } finally {
            em.close();
        }
    }
}
