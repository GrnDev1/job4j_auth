package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.dto.UserDto;
import ru.job4j.auth.handlers.Operation;
import ru.job4j.auth.service.PersonService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
@Slf4j
@Validated
public class PersonController {
    private final PersonService persons;

    @GetMapping("/")
    public ResponseEntity<Collection<Person>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(persons.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable @Min(1) int id) {
        return persons.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Person with this id is not found"
                ));
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@Validated(Operation.OnCreate.class) @RequestBody Person person) {
        return persons.save(person)
                .map(p -> new ResponseEntity<>(p, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@Validated(Operation.OnUpdate.class) @RequestBody Person person) {
        return persons.update(person) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserDto userDto) {
        return persons.updatePassword(userDto) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) int id) {
        var res = persons.deleteById(id);
        return res ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}