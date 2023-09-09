package ru.nasrulaev.spring.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.nasrulaev.spring.models.Book;
import ru.nasrulaev.spring.services.BooksService;

@Component
public class BookValidator implements Validator {

    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Book.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (booksService.uniqueCheck(book.getAuthorName(), book.getTitle(), book.getPublicationYear()))
            errors.rejectValue("authorName", "", "Books must be unique");
    }
}
