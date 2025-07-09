package com.chessd.chess.game.service;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.utils.GameResult;

public interface EndGameService {
    boolean lookForEndGame(Game game, String color);

    void handleAfterGame(Game game);

    void handleAfterGame(Game game, GameResult gameResult);
}
