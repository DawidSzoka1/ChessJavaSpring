package com.chessd.chess.service.figureService;

import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.service.CheckService;
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
    private final CheckService checkService;

    public KingMoveService(FigureDao figureDao, MoveService moveService, CheckService checkService) {
        this.figureDao = figureDao;
        this.moveService = moveService;
        this.checkService = checkService;
    }

    @Override
    public List<String> getAvailableMoves(Figure figure, HashMap<Position, Figure> board) {
        List<String> moves = new ArrayList<>();
        int[] horizontal = {1, 0, -1};
        int[] vertical = {1, 0, -1};
        int newRow;
        int newCol;
        Position p;
        for(int row: vertical){
            for(int col: horizontal){
                newRow = figure.getRow() + row;
                newCol = figure.getCol() + col;
                if(moveService.validRowCol(newRow, newCol)){
                    p = Position.fromRowCol(newRow, newCol).get();
                    Figure potentialF = board.get(p);
                    if(potentialF == null || potentialF.getOpponent().equals(figure.getColor())){
                        if(checkService.isKingSafeAfterMove(figure, p.toString(), figure.getGame())) {
                            moves.add(p.toString());
                        }
                    }
                }
            }
        }
        return moves;
    }
}
