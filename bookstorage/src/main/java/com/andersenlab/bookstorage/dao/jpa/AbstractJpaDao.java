package com.andersenlab.bookstorage.dao.jpa;

import com.andersenlab.bookstorage.dao.Dao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractJpaDao<E, PK extends Serializable> implements Dao<E, PK> {

    protected EntityManagerProvider entityManagerProvider;

    public AbstractJpaDao(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public E getById(PK id) {
        EntityManager em = entityManagerProvider.getEntityManager();
        return em.find(getEntityClass(), id);
    }

    @Override
    public E create(E entity) {
        EntityManager em = entityManagerProvider.getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    @Override
    public List<E> getAll() {
        EntityManager em = entityManagerProvider.getEntityManager();
        CriteriaQuery<E> cq = em.getCriteriaBuilder().createQuery(getEntityClass());
        Root<E> rootEntry = cq.from(getEntityClass());
        cq = cq.select(rootEntry);
        return em.createQuery(cq).getResultList();
    }

    protected abstract Class<E> getEntityClass();

}
