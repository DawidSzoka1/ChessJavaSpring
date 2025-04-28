package com.chessd.chess.user.controller;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import com.chessd.chess.user.web.UpdateUser;
import com.chessd.chess.webSocket.utils.SessionHelper;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UpdateController {
    private final UserService userService;
    private final SessionHelper sessionHelper;

    @Autowired
    public UpdateController(UserService userService, SessionHelper sessionHelper) {
        this.userService = userService;
        this.sessionHelper = sessionHelper;
    }

    private String validUpdateUser(UpdateUser updateUser, User user) {
        User emailTaken = userService.findByEmail(updateUser.getEmail());
        User userNameTaken = userService.findByUserName(updateUser.getUserName());
        if (emailTaken != null && emailTaken.getId() != user.getId()) {
            return "Email jest zajęty!!";
        }
        if (userNameTaken != null && userNameTaken.getId() != user.getId()) {
            return "Pseudonim jest zajęty!!";
        }
        return "";
    }


    @GetMapping("/update")
    public String updateInfo(Model model, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        UpdateUser updateUser = UpdateUser.fromUser(user);
        model.addAttribute("user", updateUser);
        return "users/update";
    }

    @PostMapping("/update")
    public RedirectView updateProcess(
            @Valid @ModelAttribute("user") UpdateUser updateUser,
            BindingResult bindingResult,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for(ObjectError error : bindingResult.getAllErrors()){
                errorMessages.add(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("error", errorMessages);
            return new RedirectView("/users/update");
        }
        User user = sessionHelper.getUserFromPrincipal(session);
        String check = this.validUpdateUser(updateUser, user);
        System.out.println("Przeszlo checka");
        if (!check.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", check);
            return new RedirectView("/users/update");
        }
        if (!updateUser.getUserName().equals(user.getUserName())) {
            sessionHelper.updateUserInSession(updateUser, session);
        }
        userService.update(user.getId(), updateUser);

        redirectAttributes.addFlashAttribute("success", "Dane zostały zmienione pomyślnie");
        return new RedirectView("/users/profile/"+ user.getUserName());
    }
}
