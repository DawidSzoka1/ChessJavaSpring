package com.chessd.chess.repository;


import com.chessd.chess.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void save(User user);

    User findByUserName(String username);

    //    User findByEmail(String email);
    void delete(int id);

    void update(User user);

    List<User> findAll();

    List<User> findAllSortedByNameASC();

    Optional<User> findById(int id);
}
