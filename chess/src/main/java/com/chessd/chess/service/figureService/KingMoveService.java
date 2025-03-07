package com.chessd.chess.service.figureService;

import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.service.MoveService;
import com.chessd.chess.utils.Column;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<String> getAvaibleMoves(Figure figure, List<Figure> board) {
        List<String> moves = new ArrayList<>();

        return moves;
    }
}
