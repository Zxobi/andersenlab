package com.andersenlab.bookstorage.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateEntityManagerProvider implements EntityManagerProvider {

    private EntityManagerFactory entityManagerFactory;

    public HibernateEntityManagerProvider() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("com.andersenlab.bookstorage");
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
