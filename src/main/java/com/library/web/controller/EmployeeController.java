package com.library.web.controller;

import com.library.web.model.Author;
import com.library.web.model.Employee;
import com.library.web.repository.AuthorRepository;
import com.library.web.repository.EmployeeRepository;
import com.library.web.viewmodel.CreateAuthorForm;
import com.library.web.viewmodel.UpdateAuthorForm;
import com.library.web.viewmodel.UpdateEmployeeForm;
import com.library.web.viewmodel.UpdateMemberForm;
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
public class EmployeeController {

    static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping(value = "/employee")
    public String showEmployee(Model model) {
        model.addAttribute("employeeList", employeeRepository.findAll());
        return "employee";
    }

    @RequestMapping(value = "/employee/{id}/delete")
    public String deleteEmployee(@PathVariable("id") long employeeId, Model model) {
        return employeeRepository.findByEmployeeId(employeeId)
                .map(s -> {
                    employeeRepository.deleteById(employeeId);
                    model.addAttribute("employeeList", employeeRepository.findAll());
                    return "redirect:/employee";
                })
                .orElseGet(() -> {
                    model.addAttribute("employeeList", employeeRepository.findAll());
                    return "redirect:/employee";
                });
    }

    @RequestMapping(value = "/employee/{id}/editForm")
    public String editEmployee(@PathVariable("id") long employeeId, Model model) {
        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    model.addAttribute("updateEmployeeForm", UpdateEmployeeForm.from(employee));
                    return "edit-employee";
                })
                .orElseGet(() -> {
                    model.addAttribute("employeeList", employeeRepository.findAll());
                    return "employee";
                });
    }

    // return new ModelAndView("redirect:/redirectedUrl", model); didn't work
    // https://www.baeldung.com/spring-redirect-and-forward
    @PostMapping("/employee/{id}/edit")
    public String updateAEmployee(@ModelAttribute @Valid UpdateEmployeeForm updateEmployeeForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-employee";
        } else {
            return employeeRepository.findByEmployeeId(updateEmployeeForm.getId())
                    .map(s -> {
                        employeeRepository.save(Employee.updateFrom(employeeRepository, updateEmployeeForm));
                        model.addAttribute("employeeList", employeeRepository.findAll());
                        return "redirect:/employee";
                    })
                    .orElseGet(() -> {
                        model.addAttribute("employeeList", employeeRepository.findAll());
                        return "redirect:/employee";
                    });
        }
    }
}