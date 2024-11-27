package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.gameEntity.Figure;

import java.util.List;

public interface FigureDao {
    Figure getFigureByPosition(String position);
    List<Figure> getAllFigures();
    Figure getFigureByName(String figureName);
    void save(Figure figure);
    void delete(Figure figure);
    void update(Figure figure);
    void moveToPosition(Figure figure, String position);
}
