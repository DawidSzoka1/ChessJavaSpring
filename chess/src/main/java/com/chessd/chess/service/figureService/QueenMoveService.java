package com.chessd.chess.service.figureService;

import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.utils.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class QueenMoveService implements FigureMoveService{
    private final GenericMoveService genericMoveService;

    @Autowired
    public QueenMoveService(GenericMoveService genericMoveService) {
        this.genericMoveService = genericMoveService;
    }


    @Override
    public List<String> getAvailableMoves(Figure figure, HashMap<Position, Figure> board) {
        List<String> moves = new ArrayList<>();
        moves.addAll(genericMoveService.diagonalMoves(figure, board));
        moves.addAll(genericMoveService.movesHorVer(figure, board, true));
        moves.addAll(genericMoveService.movesHorVer(figure, board, false));
        return moves;
    }
}
