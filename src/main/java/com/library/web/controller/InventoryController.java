package com.library.web.controller;

import com.library.web.model.Inventory;
import com.library.web.repository.InventoryRepository;
import com.library.web.viewmodel.CreateInventoryForm;
import com.library.web.viewmodel.UpdateInventoryForm;
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
public class InventoryController {

    static final Logger LOG = LoggerFactory.getLogger(InventoryController.class);
    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @RequestMapping(value = "/inventory")
    public String showInventory(Model model) {
        model.addAttribute("inventoryList", inventoryRepository.findAll());
        return "inventory";
    }

    @RequestMapping(value = "/inventory/createInventoryForm")
    public String showCreateInventory(Model model) {
        model.addAttribute("createInventoryForm", new CreateInventoryForm());
        return "create-inventory";
    }

    @PostMapping("/inventory")
    public String postCreateInventory(@ModelAttribute @Valid CreateInventoryForm createInventoryForm,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            return "create-inventory";
        } else {
            inventoryRepository.save(Inventory.from(createInventoryForm));
            model.addAttribute("inventoryList", inventoryRepository.findAll());
            return "inventory";
        }
    }

    @RequestMapping(value = "/inventory/{id}/delete")
    public String deleteInventory(@PathVariable("id") long inventoryId, Model model) {
        return inventoryRepository.findById(inventoryId)
                .map(s -> {
                    inventoryRepository.deleteById(inventoryId);
                    model.addAttribute("inventoryList", inventoryRepository.findAll());
                    return "redirect:/inventory";
                })
                .orElseGet(() -> {
                    model.addAttribute("inventoryList", inventoryRepository.findAll());
                    return "redirect:/inventory";
                });
    }

    @RequestMapping(value = "/inventory/{id}/editForm")
    public String editInventory(@PathVariable("id") long inventoryId, Model model) {
        return inventoryRepository.findById(inventoryId)
                .map(inventory -> {
                    model.addAttribute("updateInventoryForm", UpdateInventoryForm.from(inventory));
                    return "edit-inventory";
                })
                .orElseGet(() -> {
                    model.addAttribute("inventoryList", inventoryRepository.findAll());
                    return "inventory";
                });
    }

    @PostMapping("/inventory/{id}/edit")
    public String updateInventory(@ModelAttribute @Valid UpdateInventoryForm updateInventoryForm,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-inventory";
        } else {
            return inventoryRepository.findByInventoryId(updateInventoryForm.getInventoryId())
                    .map(s -> {
                        inventoryRepository.save(Inventory.updateFrom(inventoryRepository, updateInventoryForm));
                        model.addAttribute("inventoryList", inventoryRepository.findAll());
                        return "redirect:/inventory";
                    })
                    .orElseGet(() -> {
                        model.addAttribute("inventoryList", inventoryRepository.findAll());
                        return "redirect:/inventory";
                    });
        }
    }
}