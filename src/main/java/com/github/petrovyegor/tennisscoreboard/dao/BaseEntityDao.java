package com.github.petrovyegor.tennisscoreboard.dao;

import java.util.Optional;

public interface BaseEntityDao<T, ID> {
    Optional<T> findById(ID id);

    T save(T entity);
}
