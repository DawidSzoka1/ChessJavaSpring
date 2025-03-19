package com.chessd.chess.service;

import com.chessd.chess.entity.User;
import com.chessd.chess.web.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);
    List<User> findAll();
    List<User> findAllByRanking(int amount);
    Optional<User> findById(int id);
    void save(WebUser webUser);
    void update(WebUser webUser);
}
