package com.chessd.chess.controller.user;

import com.chessd.chess.entity.User;
import com.chessd.chess.service.UserService;
import com.chessd.chess.user.web.RegisterUser;
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
        RegisterUser registerUser = new RegisterUser();
        model.addAttribute("registerUser", registerUser);
        return "users/register";
    }

    @PostMapping("/processRegistration")
    public String processRegistration(
            @Valid @ModelAttribute("registerUser") RegisterUser registerUser,
            BindingResult bindingResult,
            HttpSession session,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "users/register";
        }

        User existing = userService.findByUserName(registerUser.getUserName());
        if (existing != null) {
            model.addAttribute("registrationError", "User name already exists.");
            return "users/register";
        }
        if(!registerUser.getPassword().equals(registerUser.getPassword2())){
            model.addAttribute("registrationError", "Passwords dont match");
            return "users/register";
        }
        userService.save(registerUser);
        User user = userService.findByUserName(registerUser.getUserName());
        System.out.println(user);
        session.setAttribute("user", user.getId());

        return "index";
    }
}
