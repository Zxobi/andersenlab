package com.andersenlab.patternsSample.dao.jpa;

import com.andersenlab.patternsSample.dao.Dao;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractJpaDao<E, PK extends Serializable> implements Dao<E, PK> {

    @Override
    public E getById(PK id) {
        return HibernateUtil.INSTANCE.getSession().get(getEntityClass(), id);
    }

    @Override
    public E create(E entity) {
        Session session = HibernateUtil.INSTANCE.getSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        return entity;
    }

    @Override
    public List<E> getAll() {
        Session session = HibernateUtil.INSTANCE.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<E> cq =cb.createQuery(getEntityClass());
        Root<E> rootEntry = cq.from(getEntityClass());
        cq = cq.select(rootEntry);

        return session.createQuery(cq).getResultList();
    }

    protected abstract Class<E> getEntityClass();

}
