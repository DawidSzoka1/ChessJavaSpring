package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;

public interface CheckService {
    boolean isFieldAttacked(String move, Game game, String color);
    void lookForChecks(Game game);
    boolean isKingSafeAfterMove(Figure figure, String move, Game game);
    void restrictMovesInCheck(Game game, Figure king);
}
