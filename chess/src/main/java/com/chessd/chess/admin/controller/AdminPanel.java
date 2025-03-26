package com.chessd.chess.admin.controller;

import com.chessd.chess.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

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

    @PostMapping("delete/users")
    public String deleteUsers(@RequestParam List<Integer> userIds){
        if(userIds.isEmpty()){
            return "redirect:admin/panel";
        }
        userService.deleteUsers(userIds);
        return "redirect:admin/panel";
    }

}
