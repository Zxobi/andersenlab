package com.andersenlab.bookstorageweb.repository;

import com.andersenlab.bookstorageweb.entity.Book;
import com.andersenlab.bookstorageweb.entity.Literature;
import com.andersenlab.bookstorageweb.entity.Magazine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface LiteratureRepository extends CrudRepository<Literature, Long> {

    Collection<Literature> findAll();

    @Query(value = "From Book")
    Collection<Book> findAllBooks();

    @Query(value = "From Magazine")
    Collection<Magazine> findAllMagazines();

}
