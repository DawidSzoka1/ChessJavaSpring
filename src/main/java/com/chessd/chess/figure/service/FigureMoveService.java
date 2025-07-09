package com.chessd.chess.figure.service;

import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.utils.Position;

import java.util.HashMap;
import java.util.List;


public interface FigureMoveService {
    List<String> getAvailableMoves(Figure figure, HashMap<Position, Figure> board);
}
