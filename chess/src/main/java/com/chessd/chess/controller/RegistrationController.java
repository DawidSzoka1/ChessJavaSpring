package com.chessd.chess.controller;

import com.chessd.chess.entity.User;
import com.chessd.chess.service.UserService;
import com.chessd.chess.web.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        WebUser webUser = new WebUser();
        model.addAttribute("webUser", webUser);
        return "users/register";
    }

    @PostMapping("/processRegistration")
    public String processRegistration(
            @Valid @ModelAttribute("webUser") WebUser webUser,
            BindingResult bindingResult,
            HttpSession session,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "users/register";
        }

        User existing = userService.findByUserName(webUser.getUserName());
        if (existing != null) {
            model.addAttribute("registrationError", "User name already exists.");
            return "users/register";
        }
        if(!webUser.getPassword().equals(webUser.getPassword2())){
            model.addAttribute("registrationError", "Passwords dont match");
            return "users/register";
        }
        userService.save(webUser);

        session.setAttribute("user", webUser);

        return "index";
    }
}
