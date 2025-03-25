package com.chessd.chess.figure.service;

import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.game.service.CheckService;
import com.chessd.chess.move.service.MoveService;
import com.chessd.chess.figure.utils.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class KingMoveService implements FigureMoveService {
    private final MoveService moveService;
    private final CheckService checkService;

    @Autowired
    public KingMoveService(@Lazy MoveService moveService, @Lazy CheckService checkService) {
        this.moveService = moveService;
        this.checkService = checkService;
    }

    @Override
    public List<String> getAvailableMoves(Figure figure, HashMap<Position, Figure> board) {
        List<String> moves = new ArrayList<>();
        int[] directions = {-1, 0, 1};

        for (int rowOffset : directions) {
            for (int colOffset : directions) {
                if (rowOffset == 0 && colOffset == 0) continue;

                int newRow = figure.getRow() + rowOffset;
                int newCol = figure.getCol() + colOffset;

                if (!moveService.validRowCol(newRow, newCol)) continue;

                Position newPosition = Position.fromRowCol(newRow, newCol).orElse(null);
                if (newPosition == null) continue;

                Figure targetFigure = board.get(newPosition);
                boolean isFieldEmptyOrEnemy = (targetFigure == null || targetFigure.getColor().equals(figure.getOpponent()));
                boolean isFieldSafe = !checkService.isFieldAttacked(newPosition.toString(), figure.getGame(), figure.getOpponent());

                if (isFieldEmptyOrEnemy && isFieldSafe) {
                    moves.add(newPosition.toString());
                }
            }
        }
        return moves;
    }
}
