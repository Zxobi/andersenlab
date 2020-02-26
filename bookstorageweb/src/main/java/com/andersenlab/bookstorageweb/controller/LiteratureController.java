package com.andersenlab.bookstorageweb.controller;

import com.andersenlab.bookstorageweb.entity.Book;
import com.andersenlab.bookstorageweb.entity.Literature;
import com.andersenlab.bookstorageweb.repository.LiteratureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class LiteratureController {

    private final LiteratureRepository literatureRepository;

    public LiteratureController(LiteratureRepository literatureRepository) {
        this.literatureRepository = literatureRepository;
    }

    @GetMapping("/literature")
    public ResponseEntity<Collection<? extends Literature>> getLiterature(
            @RequestParam(value = "type", required = false) String type
    ) {
        if (type != null) {
            if (type.equals("book")) {
                return new ResponseEntity<>(literatureRepository.findAllBooks(), HttpStatus.OK);
            } else if (type.equals("magazine")) {
                return new ResponseEntity<>(literatureRepository.findAllMagazines(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(literatureRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/literature/{id}")
    public ResponseEntity<Literature> getLiteratureById(@PathVariable(value = "id") Long id) {
        Optional<Literature> literatureOptional = literatureRepository.findById(id);

        return literatureOptional.map(
                literature -> new ResponseEntity<>(literature, HttpStatus.OK)
        ).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @Transactional
    @PostMapping("/literature")
    public ResponseEntity<Literature> createLiterature(@RequestBody Literature literature) {
        if (literature == null || literature.getId() != 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        literature = literatureRepository.save(literature);
        return new ResponseEntity<>(literature, HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/literature/{id}")
    public ResponseEntity<Literature> updateLiterature(@PathVariable(value = "id") Long id, @RequestBody Literature literature) {
        Optional<Literature> curLiteratureOptional = literatureRepository.findById(id);
        if (curLiteratureOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Literature curLiterature = curLiteratureOptional.get();
        curLiterature.setTitle(literature.getTitle());
        if (literature instanceof Book) {
            if (curLiterature instanceof Book) {
                 ((Book) curLiterature).setAuthor(((Book) literature).getAuthor());
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(curLiterature, HttpStatus.OK);
    }

    @DeleteMapping("/literature/{id}")
    public ResponseEntity<?> removeLiterature(@PathVariable(value = "id") Long id) {
        if (!literatureRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        literatureRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
