package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.utils.Position;

import java.util.HashMap;

public interface MoveService {
    boolean isMoveValid(Figure figure, String to, Game game) throws Exception;

    boolean checkIfMoveInAvailableMoves(Figure figure, String newPosition, HashMap<Position, Figure> board);
    boolean validRowCol(int row, int col);
    void executeMove(Figure figure, String to, Game game);
    void handleTakingFigure(Figure figure, String to, Game game) throws Exception;
    boolean validKingMove(Figure figure, String to, Game game) throws Exception;
    void updateFiguresMove(Game game);
    void makeMove(Figure figure, String move, HashMap<Position, Figure> board);
}
