package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.JpaUtil;
import com.github.petrovyegor.tennisscoreboard.exception.DBException;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaPlayerDao implements PlayerDao {
    @Override
    public Optional<Player> findById(Long id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Player result = em.find(Player.class, id);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            throw new DBException("Failed to get player with id '%s'".formatted(id));
        }
    }

    @Override
    public List<Player> findAll() {//TODO не используется, либо удалить, либо убрать из CrudDao
        String findAllQuery = "SELECT p FROM Player p";
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.createQuery(findAllQuery, Player.class).getResultList();
        } catch (Exception e) {
            throw new DBException("Failed to get all players");
        }
    }

    @Override
    public Player save(Player player) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
            return player;
        } catch (Exception e) {
            throw new DBException("Failed to save Player with name '%s'".formatted(player.getName()));
        }
    }

    @Override
    public Optional<Player> findByName(String name) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            String findByNameQuery = "SELECT p FROM Player p WHERE p.name = :name";
            Player result = em.createQuery(findByNameQuery, Player.class).setParameter("name", name).getSingleResult();
            return Optional.ofNullable(result);
        } catch (Exception e) {
            throw new DBException("Failed to get player with name '%s'".formatted(name));
        }
    }
}