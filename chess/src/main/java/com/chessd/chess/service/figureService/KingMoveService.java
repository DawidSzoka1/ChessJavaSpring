package com.chessd.chess.service.figureService;

import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.service.MoveService;
import com.chessd.chess.utils.Column;
import com.chessd.chess.utils.Position;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class KingMoveService implements FigureMoveService {
    private final FigureDao figureDao;
    private final MoveService moveService;

    public KingMoveService(FigureDao figureDao, MoveService moveService) {
        this.figureDao = figureDao;
        this.moveService = moveService;
    }

    @Override
    public List<String> getAvailableMoves(Figure figure, HashMap<Position, Figure> board) {

        return List.of();
    }
}
