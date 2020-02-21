package com.andersenlab.patternsSample.dao.jpa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public enum HibernateUtil {
    INSTANCE;

    private SessionFactory sessionFactory;

    {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
