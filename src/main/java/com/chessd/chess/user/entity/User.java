package com.chessd.chess.user.entity;

import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.game.entity.Game;
import com.chessd.chess.ranking.entity.RankingPosition;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_picture")
    private String profilePictureFilename;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "is_guest")
    private boolean isGuest = false;

    @Column(name = "gender",
            columnDefinition = "varchar(1) check(gender in ('F', 'M')) default 'M'"
    )
    private String gender;

    @Column(name = "country")
    private String country;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp created;

    @Column(name = "enable")
    private boolean enable;

    @Column(name = "ranking")
    private int ranking;

    @ManyToMany(fetch = FetchType.EAGER, cascade =
            {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "white")
    private List<Game> gamesAsWhite = new ArrayList<>();

    @OneToMany(mappedBy = "black")
    private List<Game> gamesAsBlack = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Set<RankingPosition> rankings;

    @OneToMany(mappedBy = "winner")
    private List<Game> gameWon;

    @OneToMany(mappedBy = "ownerId")
    private List<Figure> figures;

    @PreRemove
    public void nullif() {
        gamesAsBlack.forEach(game -> game.setBlack(null));
        gamesAsWhite.forEach(game -> game.setWhite(null));
        gameWon.forEach(game -> game.setWinner(null));
        figures.forEach(f -> f.setOwnerId(null));
    }

    public String getAuthorization(){
        for(Role role : this.getRoles()){
            if(role.getName().equals("ROLE_ADMIN")){
                return "Admin";
            }
        }
        return "Użytkownik";
    }

    public User(String userName, String password, boolean enabled, int ranking) {
        this.userName = userName;
        this.password = password;
        this.enable = enabled;
        this.ranking = ranking;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
