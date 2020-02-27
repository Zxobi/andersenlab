package com.andersenlab.bookstorageweb;

import com.andersenlab.bookstorageweb.entity.PublishingHouse;
import com.andersenlab.bookstorageweb.repository.PublishingHouseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PublishingHouseRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PublishingHouseRepository publishingHouseRepository;

    @Test
    public void testFindById() {
        PublishingHouse publishingHouse = new PublishingHouse("publishing house name");
        entityManager.persist(publishingHouse);
        entityManager.flush();

        assertThat(
                publishingHouseRepository.findById(publishingHouse.getId()).get().getName()
        ).isEqualTo("publishing house name");
    }

}
