package com.github.petrovyegor.tennisscoreboard.dao;

import java.util.Optional;

public interface CrudDao<T, ID> {
    Optional<T> findById(ID id);

    T save(T entity);
}
