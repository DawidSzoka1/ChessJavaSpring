package com.chessd.chess.service;

import com.chessd.chess.entity.Game;

public interface EndGameService {
    boolean lookForEndGame(Game game, String color);
}
