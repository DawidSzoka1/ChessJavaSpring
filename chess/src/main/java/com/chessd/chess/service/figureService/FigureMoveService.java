package com.chessd.chess.service.figureService;

import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.utils.Position;

import java.util.HashMap;
import java.util.List;


public interface FigureMoveService {
    List<String> getAvailableMoves(Figure figure, HashMap<Position, Figure> board);
}
