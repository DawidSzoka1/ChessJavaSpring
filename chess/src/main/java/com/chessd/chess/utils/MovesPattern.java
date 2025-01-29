package com.chessd.chess.utils;

import com.chessd.chess.entity.figureEntity.Figure;

import java.util.ArrayList;
import java.util.List;

public class MovesPattern {
    public static List<String> diagonalMoves(Figure figure, Figure[][] board) {
        ArrayList<String> moves = new ArrayList<>();
        int[] horizontal = {-1, 1};
        int[] vertical = {-1, 1};
        for (int h : horizontal) {
            for (int v : vertical) {
                moves.addAll(figure.generateMoves(figure.getRow(), figure.getCol(), v, h, board));
            }
        }
        return moves;
    }

    public static List<String> movesHorVer(Figure figure, Figure[][] board, boolean hor) {
        ArrayList<String> moves = new ArrayList<>();
        for (int direction = -1; direction <= 1; direction += 2) {
            int rowStep = hor ? 0 : direction;
            int colStep = hor ? direction : 0;
            moves.addAll(figure.generateMoves(figure.getRow(), figure.getCol(), rowStep, colStep, board));
        }
        return moves;
    }
}

