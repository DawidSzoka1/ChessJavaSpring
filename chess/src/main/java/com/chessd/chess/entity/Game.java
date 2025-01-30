package com.chessd.chess.entity;

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

    @Column(name = "result")
    private int result;

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

    @Column(name = "check_status", columnDefinition = "number check (check_status in (1, 2, 0))")
    private int checkStatus;

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
