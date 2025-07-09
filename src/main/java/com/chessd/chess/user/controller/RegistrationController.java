package com.chessd.chess.user.controller;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import com.chessd.chess.user.web.RegisterUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;


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
    public RedirectView processRegistration(
            @Valid @ModelAttribute("registerUser") RegisterUser registerUser,
            BindingResult bindingResult,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for(ObjectError error : bindingResult.getAllErrors()){
                errorMessages.add(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("error", errorMessages);
            return new RedirectView("/register");
        }

        User existing = userService.findByUserName(registerUser.getUserName());
        if (existing != null) {
            redirectAttributes.addFlashAttribute("error", "Ten login jest już zajęty!!");
            return new RedirectView("/register");
        }
        if(!registerUser.getPassword().equals(registerUser.getPassword2())){
            redirectAttributes.addFlashAttribute("error", "Hasła nie są takie same!!");
            return new RedirectView("/register");
        }
        userService.save(registerUser);
        User user = userService.findByUserName(registerUser.getUserName());
        session.setAttribute("user", user.getId());
        redirectAttributes.addFlashAttribute("success", "Pomyślnie stworzono konto");
        return new RedirectView("/login");
    }
}
