package com.andersenlab.bookstorage.dao.jpa;

import java.io.Serializable;

public class GenericJPADao<E, PK extends Serializable> extends AbstractJpaDao<E, PK> {

    private Class<E> entityClass;

    public GenericJPADao(Class<E> entityClass, EntityManagerProvider entityManagerProvider) {
        super(entityManagerProvider);
        this.entityClass = entityClass;
    }

    @Override
    protected Class<E> getEntityClass() {
        return entityClass;
    }
}
