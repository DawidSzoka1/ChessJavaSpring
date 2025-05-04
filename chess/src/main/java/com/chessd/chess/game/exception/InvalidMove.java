package com.chessd.chess.game.exception;

public class InvalidMove extends RuntimeException {
    public InvalidMove(String message) {
        super(message);
    }
}
