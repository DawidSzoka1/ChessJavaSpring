package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;

public interface CheckService {
    void lookForChecks(Game game);
    boolean isKingSafeAfterMove(Figure figure, String move, Game game);
    void restrictMovesInCheck(Game game, Figure king);
}
