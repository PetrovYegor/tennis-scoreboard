package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.exception.DBException;
import com.github.petrovyegor.tennisscoreboard.model.PageResult;
import com.github.petrovyegor.tennisscoreboard.model.entity.Match;
import com.github.petrovyegor.tennisscoreboard.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JpaMatchDao implements BaseEntityDao<Match, Integer> {
    public Optional<Match> findById(Integer id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Match result = em.find(Match.class, id);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            log.error("Error while findById executing with parameter '%s'".formatted(id), e);
            throw new DBException("Failed to get match by id '%s'".formatted(id));
        }
    }

    public Optional<PageResult> findByName(int pageNumber, int pageSize, String playerName) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<Match> countRoot = countQuery.from(Match.class);
            countQuery.select(cb.count(countRoot));

            List<Predicate> countPredicates = new ArrayList<>();

            if (playerName != null && !playerName.trim().isEmpty()) {
                String nameInLowerCase = playerName.toLowerCase();
                countPredicates.add(cb.like(cb.lower(countRoot.get("firstPlayer").get("name")), "%" + nameInLowerCase + "%"));
                countPredicates.add(cb.like(cb.lower(countRoot.get("secondPlayer").get("name")), "%" + nameInLowerCase + "%"));
                countPredicates.add(cb.like(cb.lower(countRoot.get("winner").get("name")), "%" + nameInLowerCase + "%"));
            }

            if (!countPredicates.isEmpty()) {
                countQuery.where(cb.or(countPredicates.toArray(new Predicate[0])));
            }

            Long totalCount = em.createQuery(countQuery).getSingleResult();

            CriteriaQuery<Match> dataQuery = cb.createQuery(Match.class);
            Root<Match> dataRoot = dataQuery.from(Match.class);
            List<Predicate> dataPredicates = new ArrayList<>();
            if (playerName != null && !playerName.trim().isEmpty()) {
                String nameInLowerCase = playerName.toLowerCase();
                dataPredicates.add(cb.like(cb.lower(dataRoot.get("firstPlayer").get("name")), "%" + nameInLowerCase + "%"));
                dataPredicates.add(cb.like(cb.lower(dataRoot.get("secondPlayer").get("name")), "%" + nameInLowerCase + "%"));
                dataPredicates.add(cb.like(cb.lower(dataRoot.get("winner").get("name")), "%" + nameInLowerCase + "%"));
            }
            if (!dataPredicates.isEmpty()) {
                dataQuery.where(cb.or(dataPredicates.toArray(new Predicate[0])));
            }

            dataQuery.orderBy(cb.desc(dataRoot.get("Id")));

            TypedQuery<Match> typedQuery = em.createQuery(dataQuery);

            int offset = (pageNumber - 1) * pageSize;

            typedQuery.setFirstResult(offset);
            typedQuery.setMaxResults(pageSize);
            List<Match> content = typedQuery.getResultList();
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);
            PageResult pageResult = new PageResult(
                    content
                    , totalCount
                    , totalPages
                    , pageSize
                    , pageNumber
            );

            return Optional.ofNullable(pageResult);
        } catch (Exception e) {
            log.error("Error while findByCriteria executing with parameters", e);
            throw new DBException(e.getMessage());
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
            log.error("Error while saving match", e);
            throw new DBException("Failed to save Match with player1 id - '%s', player2 id - '%s', winner id - '%s'".formatted(entity.getFirstPlayer().getId(), entity.getSecondPlayer().getId(), entity.getWinner().getId()));
        } finally {
            em.close();
        }
    }
}

