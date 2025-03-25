package com.chessd.chess.game.entity;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.game.utils.GameResult;
import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "game")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Game {
    @Id
    @Column(name = "game_id")
    private String gameId;

    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private GameResult result = GameResult.ONGOING;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "white", referencedColumnName = "id")
    private User white;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "black", referencedColumnName = "id")
    private User black;

    @Column(name = "start", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp start;

    @Column(name = "end")
    private Timestamp end;

    @Column(name = "check_status", columnDefinition = "varchar(1) check (check_status in ('W', 'B', 'N'))")
    private String checkStatus = "N";

    @Column(name = "next_move", columnDefinition = "varchar(1) check(next_move in ('W', 'B'))")
    private String nextMove = "W";


    public Game(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "Game{" +
                "result=" + result +
                ", gameId='" + gameId + '\'' +
                ", white=" + white +
                ", black=" + black +
                ", timestamp=" + start +
                ", end=" + end +
                '}';
    }
}
