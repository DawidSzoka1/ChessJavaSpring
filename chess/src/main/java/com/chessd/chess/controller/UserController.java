package com.chessd.chess.controller;

import com.chessd.chess.entity.User;
import com.chessd.chess.service.UserService;
import com.chessd.chess.web.WebUser;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/all";
    }

    @GetMapping("/update")
    public String updateInfo(Model model, Principal principal){
        User user = userService.findByUserName(principal.getName());
        model.addAttribute("user", user);
        return "users/update";
    }

    @PostMapping("/update")
    public String updateProcess(
            @Valid @ModelAttribute("user") WebUser webUser,
            BindingResult bindingResult,
            HttpSession session,
            Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "users/update";
        }
        Object obj = session.getAttribute("user");
        assert obj instanceof WebUser;
        WebUser user = (WebUser) obj;
        if(!webUser.equals(user)){
            model.addAttribute("error",
                    "Try to change values for different user");
            return "users/update";
        }

        userService.update(webUser);
        model.addAttribute("success", "Successfully update info");
        return "redirect:/users/profile";
    }

    @GetMapping("/rankings")
    public String rankings(Model model){
        List<User> users = userService.findAllByRanking(5);
        model.addAttribute("users", users);
        return "users/rankings";
    }
}
