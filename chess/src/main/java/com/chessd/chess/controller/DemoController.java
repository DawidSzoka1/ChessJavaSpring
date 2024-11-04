package com.chessd.chess.controller;

import com.chessd.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
    private UserService userSerivce;

    @Autowired
    public DemoController(UserService userSerivce) {
        this.userSerivce = userSerivce;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", userSerivce.findByUserName("admin"));
        return "index";
    }
}
