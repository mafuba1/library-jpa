package ru.nasrulaev.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nasrulaev.spring.services.BooksService;
import ru.nasrulaev.spring.validators.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final BookValidator bookValidator;


    @Autowired
    public BooksController(BooksService booksService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.bookValidator = bookValidator;
    }
}
