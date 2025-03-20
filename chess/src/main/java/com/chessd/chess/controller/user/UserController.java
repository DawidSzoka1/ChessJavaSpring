package com.chessd.chess.controller.user;

import com.chessd.chess.entity.User;
import com.chessd.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/rankings")
    public String rankings(Model model) {
        List<User> users = userService.findAllByRanking(6);
        model.addAttribute("first", users.getFirst());
        model.addAttribute("users", users.subList(1, users.size()));
        return "users/rankings";
    }
}
