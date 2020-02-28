package com.library.web.controller;


import com.library.web.model.Author;
import com.library.web.model.Shelf;
import com.library.web.repository.ShelfRepository;
import com.library.web.viewmodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

@Controller
public class ShelfController {

    private static final Logger LOG = LoggerFactory.getLogger(ShelfController.class);
    private final ShelfRepository shelfRepository;

    public ShelfController(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    @RequestMapping(value = "/shelf")
    public String showShelf(Model model) {
        model.addAttribute("shelfList", shelfRepository.findAll());
        return "shelf";
    }

    @RequestMapping(value = "/shelf/createShelfForm")
    public String showCreateShelf(Model model) {
        model.addAttribute("createShelfForm", new CreateShelfForm());
        return "create-shelf";
    }

    @PostMapping("/shelf")
    public String postCreateShelf(@ModelAttribute @Valid CreateShelfForm createShelfForm,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            return "create-shelf";
        } else {
            return shelfRepository.findByCode(createShelfForm.getCode())
                    .map(s -> {
                        model.addAttribute("shelfList",true);
                        return "create-shelf";
                    })
                    .orElseGet(() -> {
                        shelfRepository.save(Shelf.from(createShelfForm));
                        model.addAttribute("shelfList", shelfRepository.findAll());
                        return "shelf";
                    });
        }
    }

    @RequestMapping(value = "/shelf/{id}/delete")
    public String deleteShelf(@PathVariable("id") long shelfId, Model model) {
        return shelfRepository.findById(shelfId)
                .map(s -> {
                    shelfRepository.deleteById(shelfId);
                    model.addAttribute("shelfList", shelfRepository.findAll());
                    return "redirect:/shelf";
                })
                .orElseGet(() -> {
                    model.addAttribute("shelfList", shelfRepository.findAll());
                    return "redirect:/shelf";
                });
    }

    @RequestMapping(value = "/shelf/{id}/editForm")
    public String editShelf(@PathVariable("id") long shelfId, Model model) {
        return shelfRepository.findById(shelfId)
                .map(shelf -> {
                    model.addAttribute("updateShelfForm", UpdateShelfForm.from(shelf));
                    return "edit-shelf";
                })
                .orElseGet(() -> {
                    model.addAttribute("shelfList", shelfRepository.findAll());
                    return "shelf";
                });
    }

    // is this way fine? so we can return it with some model attribute saying there is an error while updating
    // put ignore until figure this out
    @PostMapping("/shelf/{id}/edit")
    public String updateShelf(@ModelAttribute @Valid UpdateShelfForm updateShelfForm,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-shelf";
        } else {
            return shelfRepository.findByShelfId(updateShelfForm.getId())
                    .map(s -> {
                        try {
                            shelfRepository.save(Shelf.updateFrom(shelfRepository, updateShelfForm));
                        } catch (Exception ignored) { }
                        model.addAttribute("shelfList", shelfRepository.findAll());
                        return "redirect:/shelf";
                    })
                    .orElseGet(() -> {
                        model.addAttribute("shelfList", shelfRepository.findAll());
                        return "redirect:/shelf";
                    });
        }
    }


}