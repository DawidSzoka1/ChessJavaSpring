package com.chessd.chess.security;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    @Autowired
    public UserAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        String userName = authentication.getName();
        User theUser = userService.findByUserName(userName);
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);

        response.sendRedirect(request.getContextPath() + "/");
    }
}
