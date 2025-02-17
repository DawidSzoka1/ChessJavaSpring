package com.chessd.chess.repository;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;

import java.util.Optional;

public interface FigureDao {
    void save(Figure figure);
    void delete(Figure figure);
    void update(Figure figure);
    Optional<Figure> getFigureByPossibleMovesAndColor(Game game, String color, String move);
    Figure getKing(Game game, String color);
    Figure getFigureByPosition(String to, Game game);
}
