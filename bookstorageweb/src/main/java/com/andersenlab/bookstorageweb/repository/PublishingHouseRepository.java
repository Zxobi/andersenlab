package com.andersenlab.bookstorageweb.repository;

import com.andersenlab.bookstorageweb.entity.PublishingHouse;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PublishingHouseRepository extends CrudRepository<PublishingHouse, Long> {

    Collection<PublishingHouse> findAll();

}
