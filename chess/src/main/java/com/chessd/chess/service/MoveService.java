package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;

public interface MoveService {
    void isMoveValid(Figure figure, String to, Game game) throws Exception;
    void executeMove(Figure figure, String to, Game game);
    void handleTakingFigure(Figure figure, String to, Game game) throws Exception;
    boolean validKingMove(Figure figure, String to, Game game);
    void updateFiguresMove(Game game);
}
