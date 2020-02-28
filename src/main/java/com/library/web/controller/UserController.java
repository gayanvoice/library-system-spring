package com.library.web.controller;


import com.library.web.model.User;
import com.library.web.model.Employee;
import com.library.web.viewmodel.LoginForm;
import com.library.web.viewmodel.SignUpForm;
import com.library.web.repository.EmployeeRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    private final EmployeeRepository employeeRepository;

    public UserController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }


    @RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
    public String loginError(Model model) {
        return "login";
    }

    @RequestMapping(value = "/doLogout", method = RequestMethod.GET)
    public String logout(SessionStatus session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        return "login";

    }

    // asked to put this in to a service later
    @RequestMapping(value = "/postLogin", method = RequestMethod.POST)
    public String postLogin(Model model, HttpSession session) {
        System.out.println("postLogin()");
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrinciple(authentication.getPrincipal());
        Employee loggedInUser = ((User) authentication.getPrincipal()).getUserDetails();
        session.setAttribute("userId", loggedInUser.getEmployeeId());
        return "dashboard";
    }

    private void validatePrinciple(Object principal) {
        if (!(principal instanceof User)) {
            throw new  IllegalArgumentException("Principal can not be null!");
        }
    }

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpSubmit(@ModelAttribute @Valid SignUpForm signUpForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "signup";
        } else {
            return employeeRepository.findByUsername(signUpForm.getUsername())
                    .map(employee -> {
                        return "signup";
                    })
                    .orElseGet(() -> {
                        employeeRepository.save(Employee.from(signUpForm));
                        model.addAttribute("loginForm", new LoginForm());
                        return "login";
                    });
        }
    }


}