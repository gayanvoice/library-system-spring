package com.library.web.controller;

import com.library.web.model.Author;
import com.library.web.repository.AuthorRepository;
import com.library.web.viewmodel.CreateAuthorForm;
import com.library.web.viewmodel.UpdateAuthorForm;
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
public class AuthorController {

    static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @RequestMapping(value = "/author")
    public String showAuthor(Model model) {
        model.addAttribute("authorList", authorRepository.findAll());
        return "author";
    }

    @RequestMapping(value = "/author/createAuthorForm")
    public String showCreateAuthor(Model model) {
        model.addAttribute("createAuthorForm", new CreateAuthorForm());
        return "create-author";
    }

    @PostMapping("/author")
    public String postCreateAuthor(@ModelAttribute @Valid CreateAuthorForm createAuthorForm,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            return "create-author";
        } else {
            return authorRepository.findByName(createAuthorForm.getName())
                    .map(s -> {
                        model.addAttribute("authorExist", true);
                        return "create-author";
                    })
                    .orElseGet(() -> {
                        authorRepository.save(Author.from(createAuthorForm));
                        model.addAttribute("authorList", authorRepository.findAll());
                        return "author";
                    });
        }
    }

    @RequestMapping(value = "/author/{id}/delete")
    public String deleteAuthor(@PathVariable("id") long authorId, Model model) {
        return authorRepository.findById(authorId)
                .map(s -> {
                    authorRepository.deleteById(authorId);
                    model.addAttribute("authorList", authorRepository.findAll());
                    return "redirect:/author";
                })
                .orElseGet(() -> {
                    model.addAttribute("authorList", authorRepository.findAll());
                    return "redirect:/author";
                });
    }

    @RequestMapping(value = "/author/{id}/editForm")
    public String editAuthor(@PathVariable("id") long authorId, Model model) {
        return authorRepository.findById(authorId)
                .map(author -> {
                    model.addAttribute("updateAuthorForm", UpdateAuthorForm.from(author));
                    return "edit-author";
                })
                .orElseGet(() -> {
                    model.addAttribute("authorList", authorRepository.findAll());
                    return "author";
                });
    }

    // return new ModelAndView("redirect:/redirectedUrl", model); didn't work
    // https://www.baeldung.com/spring-redirect-and-forward
    @PostMapping("/author/{id}/edit")
    public String updateAuthor(@ModelAttribute @Valid UpdateAuthorForm updateAuthorForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-author";
        } else {
            return authorRepository.findByAuthorId(updateAuthorForm.getId())
                    .map(s -> {
                        authorRepository.save(Author.updateFrom(authorRepository, updateAuthorForm));
                        model.addAttribute("authorList", authorRepository.findAll());
                        return "redirect:/author";
                    })
                    .orElseGet(() -> {
                        model.addAttribute("authorList", authorRepository.findAll());
                        return "redirect:/author";
                    });
        }
    }
}