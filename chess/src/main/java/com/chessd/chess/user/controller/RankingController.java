package com.chessd.chess.user.controller;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class RankingController {

    UserService userService;

    @Autowired
    public RankingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/rankings")
    public String rankings(Model model) {
        List<User> users = userService.findAllByRanking(6);
        model.addAttribute("users", users);
        return "users/rankings";
    }
}
