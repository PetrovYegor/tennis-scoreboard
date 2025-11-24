package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.JpaUtil;
import com.github.petrovyegor.tennisscoreboard.dto.match.PageResultDto;
import com.github.petrovyegor.tennisscoreboard.exception.DBException;
import com.github.petrovyegor.tennisscoreboard.model.entity.Match;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaMatchDao implements CrudDao<Match, Integer> {
    public Optional<Match> findById(Integer id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Match result = em.find(Match.class, id);
            return Optional.ofNullable(result);
        }//TODO в репах сделать Catch блоки, ведь можно постманом подавать невалидные идшники
    }

    @Override
    public List<Match> findAll() {
        return null;
    }//это либо убрать, либо переписать

//    @Override
//    public List<Match> findAll() {
//        //return List.of();
//        ArrayList<Match> temp = new ArrayList<>();
//        return temp;
//    }

    public Optional<PageResultDto> findByCriteria(int pageNumber, int pageSize, String playerName) {//мб создать под этот метод отделный интерфейс MatchDao
        try (EntityManager em = JpaUtil.getEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            // Определяем корневую сущность - откуда считаем (FROM Match)
            Root<Match> countRoot = countQuery.from(Match.class);
            countQuery.select(cb.count(countRoot));//хотим получить COUNT (подсчет количества)

            //Список стейтментов для Where
            List<Predicate> countPredicates = new ArrayList<>();

            if (playerName != null && !playerName.trim().isEmpty()) {
                countPredicates.add(cb.like(countRoot.get("firstPlayer").get("name"), "%" + playerName + "%"));
                countPredicates.add(cb.like(countRoot.get("secondPlayer").get("name"), "%" + playerName + "%"));
                countPredicates.add(cb.like(countRoot.get("winner").get("name"), "%" + playerName + "%"));
            }

            if (!countPredicates.isEmpty()) {
                countQuery.where(cb.or(countPredicates.toArray(new Predicate[0])));
            }

            //Выполняем Count запрос
            Long totalCount = em.createQuery(countQuery).getSingleResult();


            CriteriaQuery<Match> dataQuery = cb.createQuery(Match.class);
            Root<Match> dataRoot = dataQuery.from(Match.class);
            List<Predicate> dataPredicates = new ArrayList<>();
            if (playerName != null && !playerName.trim().isEmpty()) {
                dataPredicates.add(cb.like(dataRoot.get("firstPlayer").get("name"), "%" + playerName + "%"));
                dataPredicates.add(cb.like(dataRoot.get("secondPlayer").get("name"), "%" + playerName + "%"));
                dataPredicates.add(cb.like(dataRoot.get("winner").get("name"), "%" + playerName + "%"));
            }
            if (!dataPredicates.isEmpty()) {
                dataQuery.where(cb.or(dataPredicates.toArray(new Predicate[0])));
            }

            //Добавить сортировку (важно для пагинации)
            dataQuery.orderBy(cb.desc(dataRoot.get("Id")));

            //Создать и выполнить запрос
            TypedQuery<Match> typedQuery = em.createQuery(dataQuery);

            //Смещение
            int offset = (pageNumber - 1) * pageSize;

            //установка пагинации
            typedQuery.setFirstResult(offset);//смещение offset
            typedQuery.setMaxResults(pageSize);//лимит
            //основной контент для отображения в jsp
            List<Match> content = typedQuery.getResultList();

            //СБОРКА РЕЗУЛЬТАТА

            //Общее количество страниц
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);

            //Создать и вернуть объект пагинации
            //PageResultDto pageResultDto = null;
            PageResultDto pageResultDto = new PageResultDto(
                    content
                    , totalCount
                    , totalPages
                    , pageSize
                    , pageNumber
            );


            return Optional.ofNullable(pageResultDto);
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
            throw new DBException("Failed to save Match with player1 id - '%s', player2 id - '%s', winner id - '%s'".formatted(entity.getFirstPlayer().getId(), entity.getSecondPlayer().getId(), entity.getWinner().getId()));
        } finally {
            em.close();
        }
    }
}
