package com.chessd.chess.global.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class DemoController {
    private Set<String> loggedUsers = new HashSet<>();

    @GetMapping("/demo")
    public String demo(@AuthenticationPrincipal UserDetails userDetails, Model model){
        System.out.println(userDetails);
        if(userDetails != null){
            loggedUsers.add(userDetails.getUsername());
        }else{
            loggedUsers.add("null user  ");
        }
        model.addAttribute("loggedUsers", loggedUsers);
        return "demo";
    }
}
