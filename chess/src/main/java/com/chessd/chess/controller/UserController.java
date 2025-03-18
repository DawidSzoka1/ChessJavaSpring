package com.chessd.chess.controller;

import com.chessd.chess.entity.User;
import com.chessd.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public String details(Model model, @PathVariable int id) throws Exception {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new Exception("user not found");
        }
        model.addAttribute("user", user.get());
        return "profile";
    }

    @GetMapping("/rankings")
    public String rankings(Model model){
        List<User> users = userService.findAllByRanking(5);
        model.addAttribute("users", users);
        return "users/rankings";
    }
}
