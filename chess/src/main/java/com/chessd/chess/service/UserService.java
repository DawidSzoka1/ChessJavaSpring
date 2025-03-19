package com.chessd.chess.service;

import com.chessd.chess.entity.User;
import com.chessd.chess.user.web.RegisterUser;
import com.chessd.chess.user.web.UpdateUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);

    User findByEmail(String email);

    List<User> findAll();

    List<User> findAllByRanking(int amount);

    Optional<User> findById(int id);

    void save(RegisterUser registerUser);

    void update(int id, UpdateUser user);
}
