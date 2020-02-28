package com.library.web.controller;

import com.library.web.model.Book;
import com.library.web.model.Shelf;
import com.library.web.repository.AuthorRepository;
import com.library.web.repository.BookRepository;
import com.library.web.repository.ShelfRepository;
import com.library.web.viewmodel.CreateBookForm;
import com.library.web.viewmodel.UpdateAuthorForm;
import com.library.web.viewmodel.UpdateBookForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class BookController {

    static final Logger LOG = LoggerFactory.getLogger(BookController.class);
    private final BookRepository bookRepository;
    private final ShelfRepository shelfRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository,ShelfRepository shelfRepository,
                          AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.shelfRepository = shelfRepository;
        this.authorRepository = authorRepository;
    }

    @RequestMapping(value = "/book")
    public String showBook(Model model) {
        model.addAttribute("bookList", bookRepository.findAll());
        return "book";
    }

    @RequestMapping(value = "/book/createBookForm")
    public String showCreateBook(Model model) {
        model.addAttribute("createBookForm", new CreateBookForm());
        model.addAttribute("shelfList", shelfRepository.findAll());
        return "create-book";
    }

    @PostMapping("/book")
    public String postCreateBook(@ModelAttribute @Valid CreateBookForm createBookForm,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            return "create-book";
        } else {
            return bookRepository.findByTitle(createBookForm.getTitle())
                    .map(s -> {
                        model.addAttribute("bookExist", true);
                        return "create-book";
                    })
                    .orElseGet(() -> {
                        bookRepository.save(Book.from(createBookForm));
                        model.addAttribute("bookList", bookRepository.findAll());
                        return "book";
                    });
        }
    }

    @RequestMapping(value = "/book/{id}/delete")
    public String deleteBook(@PathVariable("id") long bookId, Model model) {
        return bookRepository.findById(bookId)
                .map(s -> {
                    bookRepository.deleteById(bookId);
                    model.addAttribute("bookList", bookRepository.findAll());
                    return "redirect:/book";
                })
                .orElseGet(() -> {
                    model.addAttribute("bookList", bookRepository.findAll());
                    return "redirect:/book";
                });
    }

    @RequestMapping(value = "/book/{id}/editForm")
    public String editBook(@PathVariable("id") long bookId, Model model) {
        return bookRepository.findByBookId(bookId)
                .map(book -> {
                    model.addAttribute("updateBookForm", UpdateBookForm.from(book));
                    return "edit-book";
                })
                .orElseGet(() -> {
                    model.addAttribute("bookList", bookRepository.findAll());
                    model.addAttribute("shelfList", shelfRepository.findAll());
                    model.addAttribute("authorList", authorRepository.findAll());
                    return "book";
                });
    }

    @PostMapping("/book/{id}/edit")
    public String updateBook(@ModelAttribute @Valid UpdateBookForm updateBookForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-book";
        } else {
            return bookRepository.findByBookId(updateBookForm.getId())
                    .map(s -> {
                        bookRepository.save(Book.updateFrom(bookRepository, updateBookForm));
                        model.addAttribute("bookList", bookRepository.findAll());
                        return "redirect:/book";
                    })
                    .orElseGet(() -> {
                        model.addAttribute("bookList", bookRepository.findAll());
                        return "redirect:/book";
                    });
        }
    }
}