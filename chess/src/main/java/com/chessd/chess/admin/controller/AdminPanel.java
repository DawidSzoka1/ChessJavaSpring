package com.chessd.chess.admin.controller;

import com.chessd.chess.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminPanel {
    private final UserService userService;

    @Autowired
    public AdminPanel(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/panel")
    public String adminPanel(Model model) {
        model.addAttribute("users", userService.findAllSortedByNameASC());
        return "admin/adminPanel";
    }


}
