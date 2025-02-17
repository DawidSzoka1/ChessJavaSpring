package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;

public interface MoveService {
    boolean isMoveValid(Figure figure, String to, Game game);
    void executeMove(Figure figure, String to, Game game);
    boolean handleTakingFigure(Figure figure, String to, Game game);
    boolean validKingMove(Figure figure, String to, Game game);
    boolean isMoveEscapingChecK(Figure figure, String move, Game game);
}
