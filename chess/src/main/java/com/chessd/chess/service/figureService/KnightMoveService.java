package com.chessd.chess.service.figureService;

import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.utils.Position;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class KnightMoveService implements FigureMoveService {
    @Override
    public List<String> getAvailableMoves(Figure figure, HashMap<Position, Figure> board) {
        return List.of();
    }
}
