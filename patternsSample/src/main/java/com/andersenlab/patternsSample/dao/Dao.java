package com.andersenlab.patternsSample.dao;

import java.util.List;

public interface Dao<E, PK> {

    E getById(PK id);
    E create(E entity);
    List<E> getAll();

}
