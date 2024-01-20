package ru.job4j.auth.service;

import ru.job4j.auth.domain.Person;
import ru.job4j.auth.dto.UserDto;

import java.util.Collection;
import java.util.Optional;

public interface PersonService {
    Optional<Person> findById(int id);

    Collection<Person> findAll();

    Optional<Person> save(Person person);

    boolean update(Person person);

    boolean updatePassword(UserDto userDto);

    boolean deleteById(int id);
}
