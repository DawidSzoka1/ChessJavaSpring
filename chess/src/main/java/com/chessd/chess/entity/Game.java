package com.chessd.chess.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

import com.chessd.chess.utils.Figure;

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


    @Transient
    private Figure[][] board;

    public Game() {
        this.board = new Figure[8][8];
    }

    public Game(String gameId) {
        this.gameId = gameId;
        this.board = new Figure[8][8];
    }

    public Game(String gameId, int result, User white, User black, Timestamp start, Timestamp end) {
        this.gameId = gameId;
        this.result = result;
        this.white = white;
        this.black = black;
        this.start = start;
        this.end = end;
        this.board = new Figure[8][8];

    }

    public Figure[][] getBoard() {
        return board;
    }

    public void setBoard(Figure[][] board) {
        this.board = board;
    }

    public void placeFigure(int row, int col, Figure figure) {
        this.board[row][col] = figure;
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
                "board" + Arrays.deepToString(board) +
                '}';
    }
}
