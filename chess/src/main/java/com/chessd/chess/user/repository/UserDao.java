package com.chessd.chess.user.repository;


import com.chessd.chess.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void save(User user);

    User findByUserName(String username);

    User findByEmail(String email);

    //    User findByEmail(String email);
    void delete(int id);

    void update(User user);

    List<User> findALlByRanking();

    List<User> findAll();

    List<User> findAllSortedByNameASC();

    Optional<User> findById(int id);
}
