package com.chessd.chess.game.service;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.entity.Figure;

public interface CheckService {
    boolean isFieldAttacked(String move, Game game, String color);
    void lookForChecks(Game game);
    boolean isKingSafeAfterMove(Figure figure, String move, Game game);
    void restrictMovesInCheck(Game game, Figure king);
}
