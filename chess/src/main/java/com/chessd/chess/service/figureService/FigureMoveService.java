package com.chessd.chess.service.figureService;

import com.chessd.chess.entity.figureEntity.Figure;

import java.util.List;


public interface FigureMoveService {
    List<String> getAvaibleMoves(Figure figure, List<Figure> board);
}
