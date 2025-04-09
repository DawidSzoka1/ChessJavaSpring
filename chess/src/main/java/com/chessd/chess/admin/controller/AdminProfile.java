package com.chessd.chess.admin.controller;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminProfile {

    private final UserService userService;

    public AdminProfile(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/{adminName}")
    public String getProfile(@PathVariable String adminName, Model model){
        User admin = userService.findByUserName(adminName);
        model.addAttribute("user", admin);
        return "admin/adminProfile";
    }
}
