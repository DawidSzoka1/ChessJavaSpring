package com.chessd.chess.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "user")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "is_guest")
    private boolean isGuest = false;

    @Column(name = "gender",
            columnDefinition = "varchar(1) check(gender in ('F', 'M')) default 'M'"
    )
    private String gender;

    @Column(name = "country")
    private String country;

    @Column(name = "enable")
    private boolean enable;

    @Column(name = "ranking")
    private int ranking;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;


    public User(String userName, String password, boolean enabled, int ranking) {
        this.userName = userName;
        this.password = password;
        this.enable = enabled;
        this.ranking = ranking;
    }

    public String getFullName(){
        String fullName = (this.getFirstName() == null ? "" : (this.getFirstName() + " ") ) +
                (this.getLastName() == null ? "" : this.getLastName());
        if(fullName.isEmpty()){
            fullName = "brak danych";
        }
        return fullName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enable +
                ", roles=" + roles
                + '}';
    }
}
