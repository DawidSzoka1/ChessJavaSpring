package com.chessd.chess.user.service;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.repository.RoleDao;
import com.chessd.chess.user.repository.UserDao;
import com.chessd.chess.user.CustomUserDetails;
import com.chessd.chess.user.web.RegisterUser;
import com.chessd.chess.user.web.UpdateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of the {@link UserService} interface.
 * Provides services related to user management, including finding users,
 * loading user details, and managing user roles.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<User> findAllByRanking(int amount) {
        List<User> users = userDao.findALlByRanking();
        if (users.size() < amount) {
            amount = users.size();
        }
        return users.subList(0, amount);
    }

    @Override
    public List<User> findAllSortedByNameASC() {
        return userDao.findAllSortedByNameASC();
    }

    @Override
    public void delete(User user){
        userDao.delete(user.getId());
    }

    @Override
    public void deleteUsers(List<Integer> userIds) {
        for(int id: userIds){
            Optional<User> temp = this.findById(id);
            if(temp.isEmpty()){
                continue;
            }
            this.delete(temp.get());
        }
    }

    @Override
    public Optional<User> findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public void save(RegisterUser registerUser) {
        User user = new User();

        user.setUserName(registerUser.getUserName());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        user.setEnable(true);
        user.setRoles(Arrays.asList(roleDao.findByName("ROLE_BASE")));

        userDao.save(user);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void update(int id, UpdateUser updateUser) {
        Optional<User> userOptional = userDao.findById(id);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with that id dont exist");
        }
        User user = userOptional.get();
        user.setFirstName(updateUser.getFirstName());
        user.setUserName(updateUser.getUserName());
        user.setLastName(updateUser.getLastName());
        user.setEmail(updateUser.getEmail());
        user.setCountry(updateUser.getCountry());
        user.setGender(updateUser.getGender());
        userDao.update(user);
    }

    @Override
    public User createGuestUser() {
        User guest = new User();
        guest.setUserName("Guest_" + UUID.randomUUID().toString().substring(0, 6));
        guest.setGuest(true);
        this.save(guest);
        return guest;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(
                user.getUserName(),
                user.getPassword(),
                user.isEnable(),
                user.getRoles()
        );
    }
}
