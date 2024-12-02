package com.chessd.chess.entity.gameEntity;

import com.chessd.chess.entity.User;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

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
    private Timestamp start;

    @Column(name = "end")
    private Timestamp end;

    @OneToMany(mappedBy = "game")
    private List<Figure> figures;

    public Game() {}
    public Game(String gameId) {
        this.gameId = gameId;
    }
    public Game(String gameId, int result, User white, User black, Timestamp start, Timestamp end, List<Figure> figures) {
        this.gameId = gameId;
        this.result = result;
        this.white = white;
        this.black = black;
        this.start = start;
        this.end = end;
        this.figures = figures;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }

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

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp timestamp) {
        this.start = timestamp;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
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
