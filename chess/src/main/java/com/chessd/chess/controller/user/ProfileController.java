package com.chessd.chess.controller.user;

import com.chessd.chess.entity.User;
import com.chessd.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ProfileController {
    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/profile/{userName}")
    public String showUserProfile(@PathVariable String userName, Model model){
        User user = userService.findByUserName(userName);
        if(user == null){
            throw new IllegalArgumentException("User with that id does not exist");
        }
        model.addAttribute("fullName", user.getFullName());
        model.addAttribute("user", user);
        return "users/profile";
    }
}
