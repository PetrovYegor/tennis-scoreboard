package com.github.petrovyegor.tennisscoreboard.dao;

import com.github.petrovyegor.tennisscoreboard.JpaUtil;
import com.github.petrovyegor.tennisscoreboard.dto.match.MatchRequestDto;
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
        }
    }

    @Override
    public List<Match> findAll(){
        return null;
    }

//    @Override
//    public List<Match> findAll() {
//        //return List.of();
//        ArrayList<Match> temp = new ArrayList<>();
//        return temp;
//    }

    public Optional<PageResultDto> findByCriteria(MatchRequestDto matchRequestDto) {//мб создать под этот метод отделный интерфейс MatchDao
        try (EntityManager em = JpaUtil.getEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            // Определяем корневую сущность - откуда считаем (FROM Match)
            Root<Match> countRoot = countQuery.from(Match.class);

            countQuery.select(cb.count(countRoot));//хотим получить COUNT (подсчет количества)

            //Список стейтментов для Where
            List<Predicate> predicates = new ArrayList<>();//пока явно иницилазирую TODO переписать на отдельный метод получаения
            //Динамически добавляем условия фильтрации
            if (matchRequestDto.getPlayerName() != null || !matchRequestDto.getPlayerName().isEmpty()) {
                predicates.add(cb.like(countRoot.get("Name"), matchRequestDto.getPlayerName()));
            }

            //если есть другие фильтры, то добавляем их в List

            //если список условий where не пустой, то добавляем их в запрос
            if (!predicates.isEmpty()){
                countQuery.where(cb.and(predicates.toArray(new Predicate[0])));
            }

            //Выполняем Count запрос
            Long totalElements = em.createQuery(countQuery).getSingleResult();

            //Создать запрос, который вернёт сущности Match //комментарии сохранить отдельным коммитом,
            // чтобы к нему можно было возвращаться и легче понимать, что тут написано. А потом их можно почистить
            CriteriaQuery<Match> dataQuery = cb.createQuery(Match.class);

            Root<Match> dataRoot = dataQuery.from(Match.class);

            if (!predicates.isEmpty()){
                dataQuery.where(cb.and(predicates.toArray(new Predicate[0])));
            }

            //Добавить сортировку (важно для пагинации)
            dataQuery.orderBy(cb.desc(dataRoot.get("Id")));

            //Создать и выполнить запрос
            TypedQuery<Match> typedQuery = em.createQuery(dataQuery);

            int pageSize = 5;

            //Смещение
            int offset = matchRequestDto.getPageNumber() * pageSize;

            //установка пагинации
            typedQuery.setFirstResult(offset);//смещение offset
            typedQuery.setMaxResults(pageSize);//лимит
            //основной контент для отображения в jsp
            List<Match> content = typedQuery.getResultList();

            //СБОРКА РЕЗУЛЬТАТА

            //Общее количество страниц
            int totalPages = (int)  Math.ceil((double)totalElements / pageSize);

            //Создать и вернуть объект пагинации
            PageResultDto pageResultDto = new PageResultDto(
                    totalElements
                    ,totalPages
                    ,pageSize
                    , matchRequestDto.getPageNumber()
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
            throw new DBException("Failed to save Match with player1 id - '%s', player2 id - '%s', winner id - '%s'".formatted(entity.getFirstPlayerId(), entity.getSecondPlayerId(), entity.getWinnerId()));
        } finally {
            em.close();
        }
    }
}
