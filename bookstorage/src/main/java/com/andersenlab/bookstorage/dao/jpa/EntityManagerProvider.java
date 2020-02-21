package com.andersenlab.bookstorage.dao.jpa;

import javax.persistence.EntityManager;

public interface EntityManagerProvider {

    EntityManager getEntityManager();

}
