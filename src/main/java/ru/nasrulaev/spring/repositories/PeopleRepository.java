package ru.nasrulaev.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nasrulaev.spring.models.Person;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    boolean existsPersonByName(String name);
}
