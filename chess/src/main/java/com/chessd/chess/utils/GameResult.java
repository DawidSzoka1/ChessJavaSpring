package com.chessd.chess.utils;

import java.util.HashMap;
import java.util.Map;

public enum GameResult {
    ONGOING, WHITE_WINS, BLACK_WINS, DRAW, STALEMATE, CHECKMATE;
    private static final HashMap<String, GameResult> map = (HashMap<String, GameResult>) Map.of(
            "W", GameResult.BLACK_WINS,
            "B", GameResult.WHITE_WINS,
            "N", GameResult.STALEMATE
    );
    public static GameResult fromCheckStatus(String checkStatus){
        return map.get(checkStatus);
    }
}
