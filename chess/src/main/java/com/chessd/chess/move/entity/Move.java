package com.chessd.chess.move.entity;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "move")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Move {
    @Id
    @Column(name = "move_id")
    private String moveId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private Game gameId;

    @Column(name = "start_position", columnDefinition = "varchar(4)")
    private String startPosition;

    @Column(name = "end_position", columnDefinition = "varchar(4)")
    private String endPosition;

    @Column(name = "time")
    private Timestamp time;

    @Override
    public String toString() {
        return this.startPosition  + "-" + this.endPosition;
    }
}
