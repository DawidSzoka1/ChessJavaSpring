package com.chessd.chess.user.service;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.web.RegisterUser;
import com.chessd.chess.user.web.UpdateUser;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);

    User findByEmail(String email);

    List<User> findAll();

    Page<User> findAllByRanking(int pageNumber, int pageSize);

    Page<User> findAllSortedByNameASC(int pageNumber, int pageSize);

    void deleteUsers(List<Integer> userIds);

    void delete(User user);

    Optional<User> findById(int id);

    void save(RegisterUser registerUser);

    void save(User user);

    void update(int id, UpdateUser user);

    User createGuestUser();
}
