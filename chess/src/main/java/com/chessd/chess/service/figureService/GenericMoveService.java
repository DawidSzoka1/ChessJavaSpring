package com.chessd.chess.service.figureService;

import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.service.CheckService;
import com.chessd.chess.utils.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GenericMoveService {
    private final CheckService checkService;
    private final FigureDao figureDao;

    @Autowired
    public GenericMoveService(CheckService checkService, FigureDao figureDao) {
        this.checkService = checkService;
        this.figureDao = figureDao;
    }


    public List<String> generateMoves(
            Figure figure,
            int startRow,
            int startCol,
            int rowStep,
            int colStep,
            HashMap<Position, Figure> board) {
        ArrayList<String> moves = new ArrayList<>();
        int newRow = startRow;
        int newCol = startCol;
        Position p;
        while (true) {
            newRow += rowStep;
            newCol += colStep;
            p = Position.fromRowCol(newRow, newCol).get();
            Figure check = board.get(p);
            if (check != null && check.getColor().equals(figure.getColor())) {
                break;
            }
            if(!checkService.isKingSafeAfterMove(figure, p.toString(), figure.getGame())){
                break;
            }
            moves.add(p.toString());
            if (check != null && check.getOpponent().equals(figure.getColor())) {
                break;
            }
        }

        return moves;
    }

    public List<String> diagonalMoves(Figure figure, HashMap<Position, Figure> board) {
        ArrayList<String> moves = new ArrayList<>();
        int[] horizontal = {-1, 1};
        int[] vertical = {-1, 1};
        for (int h : horizontal) {
            for (int v : vertical) {
                moves.addAll(this.generateMoves(figure, figure.getRow(), figure.getCol(), v, h, board));
            }
        }
        return moves;
    }

    public List<String> movesHorVer(Figure figure, HashMap<Position, Figure> board, boolean hor) {
        ArrayList<String> moves = new ArrayList<>();
        for (int direction = -1; direction <= 1; direction += 2) {
            int rowStep = hor ? 0 : direction;
            int colStep = hor ? direction : 0;
            moves.addAll(this.generateMoves(figure, figure.getRow(), figure.getCol(), rowStep, colStep, board));
        }
        return moves;
    }
}
