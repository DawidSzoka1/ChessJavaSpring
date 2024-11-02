package com.chessd.chess.repository;


import com.chessd.chess.entity.User;

import java.util.List;

public interface UserDao {
    void save(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    void delete(int id);
    void update(User user);
    List<User> findAll();
    List<User> findAllSortedByNameASC();
}
