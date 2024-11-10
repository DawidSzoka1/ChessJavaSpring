package com.chessd.chess.utils;

import com.chessd.chess.utils.figures.*;

public class Board {
    private Player white;
    private Player black;
    private String move;
    private String result;
    private Figure[][] board;

    public Board() {}
    public Board(Player white, Player black, String move, String result) {
        this.white = white;
        this.black = black;
        this.move = move;
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

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
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
            board[1][i] = new Pawn(1, i, true, true);
            board[6][i] = new Pawn(6, i, false, true);
            if(i == 0 || i == 7){
                board[0][i] = new Rook(0, i, true, true);
                board[7][i] = new Rook(7, i, false, true);
            }
            if(i == 1 || i == 6){
                board[0][i] = new Bishop(0, i, true, true);
                board[7][i] = new Bishop(7, i, false, true);
            }
            if (i == 2 || i == 5){
                board[0][i] = new Knight(0, i, true, true);
                board[7][i] = new Knight(7, i, false, true);
            }
        }
        board[0][4] = new King(0, 4, true, true);
        board[0][3] = new Queen(0, 3, true, true);
        board[7][4] = new King(7, 4, false, true);
        board[7][3] = new Queen(7, 3, false, true);


    }
}
