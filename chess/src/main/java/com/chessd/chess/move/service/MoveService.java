package com.chessd.chess.move.service;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.utils.Position;

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
