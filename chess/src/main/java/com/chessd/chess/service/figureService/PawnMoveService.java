package com.chessd.chess.service.figureService;

import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.entity.figureEntity.Pawn;
import com.chessd.chess.service.MoveService;
import com.chessd.chess.utils.Column;
import com.chessd.chess.utils.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PawnMoveService implements FigureMoveService {
    private final MoveService moveService;

    @Autowired
    public PawnMoveService(@Lazy MoveService moveService) {
        this.moveService = moveService;
    }

    @Override
    public List<String> getAvailableMoves(Figure figure, HashMap<Position, Figure> board) {
        ArrayList<String> moves = new ArrayList<>();
        int direction = ((Pawn) figure).getDirection();
        int row = figure.getRow();
        String col = Column.fromIndex(figure.getCol()).orElseThrow().name();
        Figure possibleBlock = board.get(Position.fromRowCol(row + direction, figure.getCol()).orElseThrow());
        if (possibleBlock == null) {
            moves.add(col + (row + direction));
        }
        if ((row == 1 && figure.getColor().equals("W")) ||
                (row == 6 && figure.getColor().equals("B"))) {
            moves.add(col + (row + direction * 2));
        }
        moves.addAll(availableTakes(figure, board));
        return moves;
    }

    public List<String> availableTakes(Figure figure, HashMap<Position, Figure> board) {
        ArrayList<String> moves = new ArrayList<>();
        int startRow = figure.getRow();
        int startCol = figure.getCol();
        for (int i = -1; i <= 1; i += 2) {
            int newRow = startRow + ((Pawn) figure).getDirection();
            int newCol = startCol + i;
            if (moveService.validRowCol(newRow, newCol)) {
                Figure toTake = board.get(Position.fromRowCol(newRow, newCol).orElseThrow());
                if (toTake != null && !toTake.getColor().equals(figure.getColor())) {
                    moves.add(toTake.getPosition().toString());
                }
            }
        }
        return moves;
    }
}
