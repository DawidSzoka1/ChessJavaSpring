package com.chessd.chess.service.figureService;

import com.chessd.chess.entity.figureEntity.Figure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RookMoveService implements FigureMoveService{
    @Override
    public List<String> getAvaibleMoves(Figure figure, List<Figure> board) {
        return List.of();
    }
}
