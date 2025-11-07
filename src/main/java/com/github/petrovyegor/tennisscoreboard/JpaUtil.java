package com.github.petrovyegor.tennisscoreboard;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory entityManagerFactory;//TODO вспомнить, что здесь происходит

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("h2-unit");
        } catch (Throwable e) {
            throw new RuntimeException("Initialization of EntityManagerFactory was failed." + e);
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
