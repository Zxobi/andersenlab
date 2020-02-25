package com.andersenlab.bookstorageweb.controller;

import com.andersenlab.bookstorageweb.entity.PublishingHouse;
import com.andersenlab.bookstorageweb.repository.PublishingHouseRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class PublishingHouseController {

    private final PublishingHouseRepository publishingHouseRepository;

    public PublishingHouseController(PublishingHouseRepository publishingHouseRepository) {
        this.publishingHouseRepository = publishingHouseRepository;
    }

    @RequestMapping(value = "/publishingHouse/{id}", method = RequestMethod.GET)
    public Optional<PublishingHouse> getPublishingHouses(@PathVariable(value = "id") Long id) {
        return publishingHouseRepository.findById(id);
    }

    @RequestMapping(value = "/publishingHouses", method = RequestMethod.GET)
    public Collection<PublishingHouse> getPublishingHouses() {
        return publishingHouseRepository.findAll();
    }

    @Transactional
    @RequestMapping(value = "/publishingHouse", method = RequestMethod.POST)
    public PublishingHouse savePublishingHouse(@RequestBody PublishingHouse publishingHouse) {
        return publishingHouseRepository.save(publishingHouse);
    }

}
