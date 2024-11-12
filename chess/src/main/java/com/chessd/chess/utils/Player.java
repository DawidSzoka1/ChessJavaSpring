package com.chessd.chess.utils;

import com.chessd.chess.entity.User;

import java.util.Objects;

public class Player {
    private User user;
    private boolean white;
    private boolean move;

    public Player(User user, boolean white, boolean move) {
        this.user = user;
        this.white = white;
        this.move = move;
    }
    public Player(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return white == player.white;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, white, move);
    }
}
