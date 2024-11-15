package com.chessd.chess.utils;

import com.chessd.chess.utils.figures.*;

public class Board {
    private Player white;
    private Player black;
    private boolean moveWhite;
    private String result;
    private Figure[][] board;

    public Board() {}
    public Board(Player white, Player black, String result) {
        this.white = white;
        this.black = black;
        this.moveWhite = true;
        this.result = result;
        this.board = new Figure[8][8];
    }

    public Player getWhite() {
        return white;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public boolean getMoveWhite() {
        return moveWhite;
    }

    public void setMoveWhite(boolean moveWhite) {
        this.moveWhite = moveWhite;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Figure[][] getBoard() {
        return board;
    }

    public void setBoard(Figure[][] board) {
        this.board = board;
    }

    public void startGame(){
        for(int i = 0; i < 8; i++){
            board[1][i] = new Pawn(i, 1, white, true);
            board[6][i] = new Pawn(i, 6, black, true);
            if(i == 0 || i == 7){
                board[0][i] = new Rook(i, 0, white, true);
                board[7][i] = new Rook(i, 7, black, true);
            }
            if(i == 1 || i == 6){
                board[0][i] = new Knight(i, 0, white, true);
                board[7][i] = new Knight(i, 7, black, true);
            }
            if (i == 2 || i == 5){
                board[0][i] = new Bishop(i, 0, white, true);
                board[7][i] = new Bishop(i, 7, black, true);
            }
        }
        board[0][4] = new King(4, 0, white, true);
        board[0][3] = new Queen(3, 0, white, true);
        board[7][4] = new King(4, 7, black, true);
        board[7][3] = new Queen(3, 7, black, true);


    }
}
