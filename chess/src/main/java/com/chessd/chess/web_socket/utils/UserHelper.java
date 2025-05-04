package com.chessd.chess.web_socket.utils;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.security.Principal;

@Component
public class UserHelper {
    private final UserService userService;

    @Autowired
    public UserHelper(UserService userService) {
        this.userService = userService;
    }

    public User userFromWebSession(WebSocketSession session) {
        Principal principal = session.getPrincipal();
        if (principal != null) {
            return userService.findByUserName(session.getPrincipal().getName());
        }
        return userService.createGuestUser();
    }
}
