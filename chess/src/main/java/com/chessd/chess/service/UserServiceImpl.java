package com.chessd.chess.service;

import com.chessd.chess.entity.Role;
import com.chessd.chess.entity.User;
import com.chessd.chess.repository.RoleDao;
import com.chessd.chess.repository.UserDao;
import com.chessd.chess.web.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<User> findAllByRanking(int amount) {
        List<User> users = userDao.findALlByRanking();
        if(users.size() < amount){
            amount = users.size();
        }
        return users.subList(0, amount);
    }

    @Override
    public Optional<User> findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public void save(WebUser webUser) {
        User user = new User();

        user.setUserName(webUser.getUserName());
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));

        user.setRoles(Arrays.asList(roleDao.findByName("ROLE_BASE")));

        userDao.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    /**
     * Maps the roles of a user to {@link GrantedAuthority} objects, which are used for authorization in Spring Security.
     *
     * @param roles The roles assigned to the user.
     * @return A collection of {@link GrantedAuthority} objects corresponding to the user's roles.
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role temp: roles){
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(temp.getName());
            authorities.add(tempAuthority);
        }
        return authorities;
    }
}
