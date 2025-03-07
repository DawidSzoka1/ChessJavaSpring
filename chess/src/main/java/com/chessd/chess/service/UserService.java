package com.chessd.chess.service;

import com.chessd.chess.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);

    List<User> findAll();

    Optional<User> findById(int id);
}
