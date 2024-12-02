package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.gameEntity.Figure;

import java.util.List;
import java.util.Optional;

public interface FigureDao {
    Optional<Figure> getFigureByPosition(String position, String gameId);
    List<Figure> getAllFigures(String gameId);
    void save(Figure figure);
    void delete(Figure figure);
    void update(Figure figure);
    void moveToPosition(Figure figure, String position);
}
