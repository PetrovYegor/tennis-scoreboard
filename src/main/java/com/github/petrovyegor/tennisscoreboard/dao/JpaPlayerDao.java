package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.JpaUtil;
import com.github.petrovyegor.tennisscoreboard.exception.DBException;
import com.github.petrovyegor.tennisscoreboard.model.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class JpaPlayerDao implements PlayerDao {
    @Override
    public Optional<Player> findById(Integer id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Player result = em.find(Player.class, id);
            return Optional.ofNullable(result);
        } catch (Throwable e) {
            throw new DBException("Failed to get player with id '%s'".formatted(id));
        }
    }

    @Override
    public List<Player> findAll() {
        String findAllQuery = "SELECT p FROM Player p";
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.createQuery(findAllQuery, Player.class).getResultList();
        } catch (Throwable e) {
            throw new DBException("Failed to get all players");
        }
    }


    @Override
    public Player save(Player entity) {
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
            throw new DBException("Failed to save Player with name '%s'".formatted(entity.getName()));
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Player> findByName(String name) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            String findByNameQuery = "SELECT p FROM Player p WHERE p.name = :name";
            Player result = em.createQuery(findByNameQuery, Player.class).setParameter("name", name).getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Throwable e) {
            throw new DBException("Failed to get player with name '%s'".formatted(name));
        }
    }
}