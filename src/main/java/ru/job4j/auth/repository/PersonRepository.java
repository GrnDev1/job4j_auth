package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Person;

import java.util.Collection;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    Collection<Person> findAll();
}