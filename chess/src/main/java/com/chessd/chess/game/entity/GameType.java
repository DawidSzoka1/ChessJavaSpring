package com.chessd.chess.game.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "game_type")
public class GameType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "time_per_player")
    private double timePerPlayer;

    @Column(name = "add_per_move")
    private double addPerMove;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GameType gameType = (GameType) o;
        return id == gameType.id && Double.compare(timePerPlayer, gameType.timePerPlayer) == 0 && Double.compare(addPerMove, gameType.addPerMove) == 0 && Objects.equals(type, gameType.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, timePerPlayer, addPerMove);
    }
}
