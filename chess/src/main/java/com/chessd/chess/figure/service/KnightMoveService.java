package com.chessd.chess.figure.service;

import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.move.service.MoveService;
import com.chessd.chess.figure.utils.Column;
import com.chessd.chess.figure.utils.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class KnightMoveService implements FigureMoveService {
    private final MoveService moveService;

    @Autowired
    public KnightMoveService(@Lazy MoveService moveService) {
        this.moveService = moveService;
    }

    @Override
    public List<String> getAvailableMoves(Figure figure, HashMap<Position, Figure> board) {
        ArrayList<String> moves = new ArrayList<>();
        int currentRow = figure.getRow();
        int currentCol = figure.getCol();
        int[] rowPossibilities = {2, 2, -2, -2, 1, 1, -1, -1};
        int[] colPossibilities = {1, -1, 1, -1, 2, -2, 2, -2};
        for (int i = 0; i < rowPossibilities.length; i++) {
            int newRow = currentRow + rowPossibilities[i];
            int newCol = currentCol + colPossibilities[i];
            if (moveService.validRowCol(newRow, newCol)) {
                moves.add(Column.fromIndex(newCol).orElseThrow().name() + newRow);
            }
        }
        return moves;
    }
}
