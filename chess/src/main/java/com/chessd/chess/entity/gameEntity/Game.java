package com.chessd.chess.entity.gameEntity;

import com.chessd.chess.entity.User;
import jakarta.persistence.*;

import java.security.Timestamp;

@Entity
@Table(name = "game")
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
    private Timestamp timestamp;

    @Column(name = "end")
    private Timestamp end;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public User getWhite() {
        return white;
    }

    public void setWhite(User white) {
        this.white = white;
    }

    public User getBlack() {
        return black;
    }

    public void setBlack(User black) {
        this.black = black;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }
}
