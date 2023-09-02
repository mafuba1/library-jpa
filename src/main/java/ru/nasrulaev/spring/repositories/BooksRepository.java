package ru.nasrulaev.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nasrulaev.spring.models.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
