package com.chessd.chess.entity.gameEntity;

import com.chessd.chess.entity.User;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "move")
public class Move {
    @Id
    @Column(name = "move_id")
    private String moveId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private Game game;

    @Column(name = "start_position", columnDefinition = "varchar(4)")
    private String startPosition;

    @Column(name = "end_position", columnDefinition = "varchar(4)")
    private String endPosition;

    @Column(name = "time")
    private Timestamp time;

    public Move(){}
    public Move(String moveId, User user, Game game, String startPosition, String endPosition, Timestamp time) {
        this.moveId = moveId;
        this.user = user;
        this.game = game;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.time = time;
    }

    public String getMoveId() {
        return moveId;
    }

    public void setMoveId(String moveId) {
        this.moveId = moveId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Move{" +
                "moveId='" + moveId + '\'' +
                ", user=" + user +
                ", game=" + game +
                ", startPosition='" + startPosition + '\'' +
                ", endPosition='" + endPosition + '\'' +
                ", time=" + time +
                '}';
    }
}
