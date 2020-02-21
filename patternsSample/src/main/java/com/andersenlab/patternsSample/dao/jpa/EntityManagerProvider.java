package com.andersenlab.patternsSample.dao.jpa;

import javax.persistence.EntityManager;

public interface EntityManagerProvider {

    EntityManager getEntityManager();

}
