package com.chessd.chess.user;

import com.chessd.chess.user.entity.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean enabled;
    private Collection<Role> roles;

    public CustomUserDetails(String username, String password, boolean enabled, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role: this.roles){
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(tempAuthority);
        }
        return authorities;
    }

}
