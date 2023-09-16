package ru.nasrulaev.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nasrulaev.spring.models.Book;
import ru.nasrulaev.spring.models.Person;
import ru.nasrulaev.spring.services.BooksService;
import ru.nasrulaev.spring.services.PeopleService;
import ru.nasrulaev.spring.validators.BookValidator;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;


    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping(params = "sort_by_year")
    public String index(@RequestParam("sort_by_year") boolean sort,
                        Model model) {
        if (!sort) return index(model);
        model.addAttribute("books", booksService.findAll(Sort.by("publicationYear")));
        return "books/index";
    }

    @GetMapping(params = {"page", "books_per_page"})
    public String index(@RequestParam("page") int page, @RequestParam("books_per_page") int booksPerPage,
                        Model model) {
        model.addAttribute("books", booksService.findAll(PageRequest.of(page, booksPerPage)));
        return "books/index";
    }

    @GetMapping(params = {"page", "books_per_page", "sort_by_year"})
    public String index(@RequestParam("page") int page, @RequestParam("books_per_page") int booksPerPage,
                        @RequestParam("sort_by_year") boolean sort, Model model) {
        if (!sort) return index(page, booksPerPage, model);
        model.addAttribute("books", booksService.findAll(
                PageRequest.of(page, booksPerPage, Sort.by("publicationYear"))));
        return "books/index";
    }


    @GetMapping("/search")
    public String search(@RequestParam(value = "pattern", required = false) String searchPattern,
                         Model model) {
        Optional<Book> book = booksService.searchByTitle(searchPattern);
        model.addAttribute("foundBook", book.orElse(null));
        book.ifPresent(value -> model.addAttribute("holder", value.getHolder()));
        return "books/search";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping
    public String addBook(@ModelAttribute("book") Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "redirect:/books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       @ModelAttribute("person") Person person, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        Person holder = booksService.getHolder(id);
        if (holder == null) model.addAttribute("people", peopleService.findAll());
        model.addAttribute("holder", holder);
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "redirect:/books";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "redirect:/books/" + id;

        booksService.setHolder(id, person);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String free(@PathVariable("id") int id) {
        booksService.free(id);
        return "redirect:/books/" + id;
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
}
