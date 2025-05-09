package com.chessd.chess.figure.service;

import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.utils.Position;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class GenericMoveService {

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
        Optional<Position> p;
        while (true) {
            newRow += rowStep;
            newCol += colStep;
            p = Position.fromRowCol(newRow, newCol);
            if(p.isEmpty()){
                break;
            }
            Figure check = board.get(p.get());
            if (check != null && check.getColor().equals(figure.getColor())) {
                break;
            }
            moves.add(p.get().toString());
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
