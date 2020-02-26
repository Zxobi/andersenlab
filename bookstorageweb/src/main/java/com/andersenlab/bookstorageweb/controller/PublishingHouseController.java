package com.andersenlab.bookstorageweb.controller;

import com.andersenlab.bookstorageweb.entity.Book;
import com.andersenlab.bookstorageweb.entity.Literature;
import com.andersenlab.bookstorageweb.entity.Magazine;
import com.andersenlab.bookstorageweb.entity.PublishingHouse;
import com.andersenlab.bookstorageweb.repository.LiteratureRepository;
import com.andersenlab.bookstorageweb.repository.PublishingHouseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class PublishingHouseController {

    private final PublishingHouseRepository publishingHouseRepository;
    private final LiteratureRepository literatureRepository;

    public PublishingHouseController(
            PublishingHouseRepository publishingHouseRepository,
            LiteratureRepository literatureRepository
    ) {
        this.publishingHouseRepository = publishingHouseRepository;
        this.literatureRepository = literatureRepository;
    }

    @GetMapping(value = "/publishingHouses")
    public ResponseEntity<Collection<PublishingHouse>> getPublishingHouses() {
        return new ResponseEntity<>(publishingHouseRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/publishingHouses/{id}")
    public ResponseEntity<PublishingHouse> getPublishingHouseById(@PathVariable(value = "id") Long id) {
        Optional<PublishingHouse> publishingHouseOptional = publishingHouseRepository.findById(id);

        return publishingHouseOptional.map(
                publishingHouse -> new ResponseEntity<>(publishingHouse, HttpStatus.OK)
        ).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @GetMapping(value = "/publishingHouses/{id}/literature")
    public ResponseEntity<Collection<Literature>> getPublishingHouseLiterature(@PathVariable(value = "id") Long id) {
        Optional<PublishingHouse> publishingHouseOptional = publishingHouseRepository.findById(id);
        if (publishingHouseOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(publishingHouseOptional.get().getLiteratures(), HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/publishingHouses/{id}/literature")
    public ResponseEntity<? extends Literature> getPublishingHouseLiterature(
            @PathVariable(value = "id") Long id,
            @RequestBody Literature literature
    ) {
        Optional<PublishingHouse> publishingHouseOptional = publishingHouseRepository.findById(id);
        if (publishingHouseOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PublishingHouse publishingHouse = publishingHouseOptional.get();
        if (literature instanceof Book) {
            literature = publishingHouse.publishBook(literature.getTitle(), ((Book) literature).getAuthor());
        } else if (literature instanceof Magazine){
            Magazine magazine = (Magazine)literature;
            if (magazine.getSerialNumber() == 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            literature = publishingHouse.publishMagazine(magazine.getTitle(), magazine.getSerialNumber());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(literature, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/publishingHouses")
    public ResponseEntity<PublishingHouse> createPublishingHouse(@RequestBody PublishingHouse publishingHouse) {
        if (publishingHouse == null || publishingHouse.getId() != 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        publishingHouse = publishingHouseRepository.save(publishingHouse);
        return new ResponseEntity<>(publishingHouse, HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping(value = "/publishingHouses/{id}")
    public ResponseEntity<PublishingHouse> updatePublishingHouse(@PathVariable(value = "id") Long id, @RequestBody PublishingHouse publishingHouse) {
        Optional<PublishingHouse> curPublishingHouseOptional = publishingHouseRepository.findById(id);
        if (curPublishingHouseOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PublishingHouse curPublishingHouse = curPublishingHouseOptional.get();
        curPublishingHouse.setName(publishingHouse.getName());

        curPublishingHouse = publishingHouseRepository.save(curPublishingHouse);

        return new ResponseEntity<>(curPublishingHouse, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(value = "/publishingHouses/{id}")
    public ResponseEntity<?> deletePublishingHouse(@PathVariable(value = "id") Long id) {
        if (!publishingHouseRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        publishingHouseRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
