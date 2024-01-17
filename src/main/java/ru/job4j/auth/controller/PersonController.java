package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.PersonService;

import java.util.Collection;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService persons;

    @GetMapping("/")
    public Collection<Person> findAll() {
        return persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return persons.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return persons.save(person)
                .map(p -> new ResponseEntity<>(p, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        return persons.update(person) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        var res = persons.deleteById(id);
        return res ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}