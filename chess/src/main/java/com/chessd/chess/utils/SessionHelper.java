package com.chessd.chess.utils;

import com.chessd.chess.entity.User;
import com.chessd.chess.service.UserService;
import com.chessd.chess.user.CustomUserDetails;
import com.chessd.chess.user.web.UpdateUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionHelper {
    private final UserService userService;

    public SessionHelper(UserService userService) {
        this.userService = userService;
    }

    public User getUserFromPrincipal(HttpSession session) {
        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication = securityContext.getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return userService.findByUserName(principal.getUsername());
    }

    public void updateUserInSession(UpdateUser user, HttpSession session){
        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication = securityContext.getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        CustomUserDetails customUserDetails = new CustomUserDetails(
                user.getUserName(),
                principal.getPassword(),
                principal.isEnabled(),
                principal.getRoles()
        );
        Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(
                customUserDetails, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
    }
}
