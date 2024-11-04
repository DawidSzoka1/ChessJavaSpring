package com.chessd.chess.service;

import com.chessd.chess.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);
}
