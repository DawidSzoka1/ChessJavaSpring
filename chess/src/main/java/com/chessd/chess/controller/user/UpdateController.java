package com.chessd.chess.controller.user;

import com.chessd.chess.entity.User;
import com.chessd.chess.service.UserService;
import com.chessd.chess.user.web.UpdateUser;
import com.chessd.chess.utils.SessionHelper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

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
            return "Email is taken";
        }
        if (userNameTaken != null && userNameTaken.getId() != user.getId()) {
            return "Username is taken";
        }
        return "";
    }


    @GetMapping("/update")
    public String updateInfo(Model model, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        model.addAttribute("fullName", user.getFullName());
        model.addAttribute("user", user);
        model.addAttribute("error", false);
        return "users/update";
    }

    @PostMapping("/update")
    public String updateProcess(
            @Valid @ModelAttribute("user") UpdateUser updateUser,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors());
            return "users/update";
        }
        User user = sessionHelper.getUserFromPrincipal(session);
        String check = this.validUpdateUser(updateUser, user);
        if (!check.isEmpty()) {
            model.addAttribute("error", check);
            return "redirect:users/update";
        }
        if (!updateUser.getUserName().equals(user.getUserName())) {
            sessionHelper.updateUserInSession(updateUser, session);
        }
        userService.update(user.getId(), updateUser);

        model.addAttribute("success", "Successfully update info");
        return "users/profile";
    }
}
