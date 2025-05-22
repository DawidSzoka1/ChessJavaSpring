package com.chessd.chess.ranking.entity;

import com.chessd.chess.game.entity.GameType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "ranking")
@AllArgsConstructor
@NoArgsConstructor
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "game_type")
    private GameType gameType;

    @OneToMany(mappedBy = "ranking")
    Set<RankingPosition> users;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ranking ranking = (Ranking) o;
        return id == ranking.id &&
                Objects.equals(name, ranking.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gameType, users);
    }
}
