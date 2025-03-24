package com.chessd.chess.game.service;

import com.chessd.chess.game.entity.Game;

public interface EndGameService {
    boolean lookForEndGame(Game game, String color);
}
