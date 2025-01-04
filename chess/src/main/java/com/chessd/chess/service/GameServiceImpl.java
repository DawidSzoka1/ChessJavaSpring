package com.chessd.chess.service;

import com.chessd.chess.entity.gameEntity.Game;
import com.chessd.chess.repository.gameRepository.GameDao;
import com.chessd.chess.repository.gameRepository.MoveDao;
import com.chessd.chess.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    private GameDao gameDao;
    private MoveDao moveDao;
    private final String[] figuresName = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};

    @Autowired
    public GameServiceImpl(GameDao gameDao, MoveDao moveDao) {
        this.gameDao = gameDao;
        this.moveDao = moveDao;
    }

    //method to create correct Figure
    public Figure putFigure(@NotNull String figureName, String color, String position) {
        return switch (figureName) {
            case "rook" -> new Rook(color, position);
            case "bishop" -> new Bishop(color, position);
            case "knight" -> new Knight(color, position);
            case "king" -> new King(color);
            case "queen" -> new Queen(color);
            default -> throw new IllegalArgumentException("Unknown figure name " + figureName);
        };
    }

    //File up the board whit Figures in starting positions
    //TODO: test for errors
    @Override
    public void startGame(@NotNull Game game) {
        for (int i = 0; i < 8; i++) {
            Column colName = Column.fromIndex(i).orElseThrow();

            //putting pawns on a2, b2 ... h2 and a7, b7 ... h7
            game.placeFigure(1, i, new Pawn("W", colName + "2"));
            game.placeFigure(6, i, new Pawn("B", colName + "7"));

            //putting correct figure on a1, b1, ..., h1 and a8, b8, ..., h8 so for white and black
            game.placeFigure(0, i, putFigure(this.figuresName[i], "W", colName + "1"));
            game.placeFigure(7, i, putFigure(this.figuresName[i], "B", colName + "8"));
        }
    }

    @Override
    public String move(String gameId, String from, String to, String color, Figure[][] lastBoard) {
        Optional<Game> game = gameDao.getGameById(gameId);
        if(game.isEmpty()){
            //TODO throw error
            return "No such game";
        }
        game.get().setBoard(lastBoard);
        Figure[][] board = game.get().getBoard();
        int col = Column.fromName(String.valueOf(from.charAt(0))).get().getIndex();
        int row = Integer.parseInt(String.valueOf(from.charAt(1))) - 1;
        Figure figure = board[row][col];
        if (figure != null) {
            return figure.toString();
        }
        ArrayList<Figure> positions = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                positions.add(board[i][j]);
            }
        }
        System.out.println(positions);
        return col + " " + row;
    }

    @Override
    public void endGame(Game game) {

    }

    @Override
    public Optional<Game> getGameById(String gameId) {
        return gameDao.getGameById(gameId);
    }


    @Override
    public Optional<Figure> getFigureById(String position, String gameId) {
        return Optional.empty();
    }
}
