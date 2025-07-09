package com.chessd.chess.game.utils;

import java.util.Map;

public enum GameResult {
    ONGOING, WHITE_WINS, BLACK_WINS, DRAW, STALEMATE, CHECKMATE;
    private static final Map<String, GameResult> map =  Map.of(
            "W", GameResult.BLACK_WINS,
            "B", GameResult.WHITE_WINS,
            "N", GameResult.STALEMATE
    );
    public static GameResult fromCheckStatus(String checkStatus){
        return map.get(checkStatus);
    }
}
