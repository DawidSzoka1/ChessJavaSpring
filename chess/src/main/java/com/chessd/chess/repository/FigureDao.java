package com.chessd.chess.repository;

import com.chessd.chess.entity.figureEntity.Figure;

public interface FigureDao {
    void save(Figure figure);
    void delete(Figure figure);
    void update(Figure figure);
}
