package com.chessd.chess.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "game")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Game {
    @Id
    @Column(name = "game_id")
    private String gameId;

    @Column(name = "result")
    private int result;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "white", referencedColumnName = "id")
    private User white;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "black", referencedColumnName = "id")
    private User black;

    @Column(name = "start")
    private Timestamp start;

    @Column(name = "end")
    private Timestamp end;

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
