package ru.nasrulaev.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nasrulaev.spring.models.Book;
import ru.nasrulaev.spring.models.Person;
import ru.nasrulaev.spring.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    public Optional<Book> searchByTitle(String title) {
        return booksRepository.findBookByTitleStartingWith(title);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void setHolder(int bookId, Person person) {
        Optional<Book> book = booksRepository.findById(bookId);
        if (book.isEmpty()) return;
        book.get().setHolder(person);
        book.get().setTakingTime(new Date());
    }

    @Transactional
    public void free(int bookId) {
        Optional<Book> book = booksRepository.findById(bookId);
        book.ifPresent(value -> value.setHolder(null));
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Person getHolder(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.map(Book::getHolder).orElse(null);
    }

    public boolean uniqueCheck(String authorName, String title, int publicationYear) {
        return booksRepository.existsByAuthorNameAndTitleAndPublicationYear(authorName, title, publicationYear);
    }
}
