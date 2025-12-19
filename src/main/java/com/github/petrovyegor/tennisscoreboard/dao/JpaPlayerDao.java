package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.exception.DBException;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;
import com.github.petrovyegor.tennisscoreboard.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class JpaPlayerDao implements PlayerDao {
    @Override
    public Optional<Player> findById(Long id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Player result = em.find(Player.class, id);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            log.error("Error searching for player with id '%s'".formatted(id), e);
            throw new DBException("Failed to get player with id '%s'".formatted(id));
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
            log.error("Error saving player '%s'".formatted(player), e);
            throw new DBException("Failed to save Player with name '%s'".formatted(player.getName()));
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
            log.error("Error while findByName executing with parameter '%s'".formatted(name), e);
            throw new DBException("Failed to get player with name '%s'".formatted(name));
        }
    }
}