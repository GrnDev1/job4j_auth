package ru.job4j.auth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SimplePersonService implements PersonService {
    private final PersonRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Optional<Person> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Collection<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Person> save(Person person) {
        try {
            person.setPassword(encoder.encode(person.getPassword()));
            repository.save(person);
            return Optional.of(person);
        } catch (Exception e) {
            log.error("Person with this mail already exists", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        Optional<Person> personOptional = repository.findById(person.getId());
        if (personOptional.isEmpty()) {
            log.error("Person with this id not found");
            return false;
        }
        repository.save(person);
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        Optional<Person> personOptional = repository.findById(id);
        if (personOptional.isEmpty()) {
            log.error("Person with this id not found");
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
