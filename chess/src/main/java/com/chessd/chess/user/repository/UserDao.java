package com.chessd.chess.user.repository;


import com.chessd.chess.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User, Integer> {

    User findUserByUserName(String userName);

    User findUserByEmail(String email);
}
