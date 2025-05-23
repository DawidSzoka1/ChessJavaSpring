package com.chessd.chess.figure.repository;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.utils.Position;

import java.util.List;
import java.util.Optional;

public interface FigureDao {
    void save(Figure figure);
    void delete(Figure figure);
    void update(Figure figure);
    Optional<Figure> getFigureByPossibleMovesAndColor(Game game, String color, String move);
    List<Figure> getAllFiguresByPossibleMoveAndColor(Game game, String color, String move);
    Figure getKing(Game game, String color);
    Figure getFigureByPosition(Position to, Game game);
    List<Figure> getAllFiguresByColor(Game game, String color);
    boolean possibleMoveByColor(Game game, String color);
    List<Figure> getAllFigureByGame(Game game);
}
