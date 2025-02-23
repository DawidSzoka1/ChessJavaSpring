package com.chessd.chess.service.figureService;

import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.utils.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BishopMoveService implements FigureMoveService{
    private final GenericMoveService genericMoveService;

    @Autowired
    public BishopMoveService(GenericMoveService genericMoveService) {
        this.genericMoveService = genericMoveService;
    }

    @Override
    public List<String> getAvailableMoves(Figure figure, HashMap<Position, Figure> board) {
        return genericMoveService.diagonalMoves(figure, board);
    }
}
