package ru.nasrulaev.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nasrulaev.spring.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findBookByTitleStartingWith(String title);

    List<Book> findBooksByHolderId(int id);

    boolean existsByAuthorNameAndTitleAndPublicationYear(String authorName, String title, int publicationYear);
}
