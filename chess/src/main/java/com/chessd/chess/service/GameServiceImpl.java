package com.chessd.chess.service;

import com.chessd.chess.entity.User;
import com.chessd.chess.entity.gameEntity.Game;
import com.chessd.chess.entity.gameEntity.Move;
import com.chessd.chess.repository.gameRepository.GameDao;
import com.chessd.chess.repository.gameRepository.MoveDao;
import com.chessd.chess.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    private GameDao gameDao;
    private MoveDao moveDao;

    @Autowired
    public GameServiceImpl(GameDao gameDao, MoveDao moveDao) {
        this.gameDao = gameDao;
        this.moveDao = moveDao;
    }

    //filling back row without king and queen for white and black
    public void fileBackRow(Game game) {
        for (int i = 0; i < 2; i++) {
            int[] knightCol = {1, 6};
            int[] bishopCol = {2, 5};
            //putting rooks on a1, h1 and a8, h8
            game.placeFigure(
                    0,
                    i * 7,
                    new Rook("W", Column.fromIndex(i * 7).get() + "1"));
            game.placeFigure(
                    7, i * 7,
                    new Rook("B", Column.fromIndex(i * 7).get() + "8"));

            // putting knights on b1, g1 and b8, g8
            game.placeFigure(
                    0,
                    knightCol[i],
                    new Knight("W", Column.fromIndex(knightCol[i]).get() + "1"));
            game.placeFigure(
                    7,
                    knightCol[i],
                    new Knight("B", Column.fromIndex(knightCol[i]).get() + "8"));

            //putting bishops on c1, f1 and c8, f8
            game.placeFigure(
                    0,
                    bishopCol[i],
                    new Bishop("W", Column.fromIndex(bishopCol[i]).get() + "1"));
            game.placeFigure(
                    7,
                    bishopCol[i],
                    new Bishop("B", Column.fromIndex(bishopCol[i]).get() + "1"));
        }
    }

    //File up the board whit Figures in starting positions
    //TODO: test for errors
    @Override
    public void startGame(Game game) {
//        TODO: file board with missing figures
        for (int i = 0; i < 8; i++) {
            //putting pawns on a2, b2 ... h2 and a7, b7 ... h7
            game.placeFigure(1, i, new Pawn("W", Column.fromIndex(i).get() + "2"));
            game.placeFigure(6, i, new Pawn("B", Column.fromIndex(i).get() + "7"));
        }
        fileBackRow(game);
        //putting kings on e1 and e8
        game.placeFigure(0, 4, new King("W"));
        game.placeFigure(7, 4, new King("B"));

        //putting queens on d1 and d8
        game.placeFigure(0, 3, new Queen("W"));//White queen on d1
        game.placeFigure(7, 3, new Queen("B"));// Black queen on d8
    }

    @Override
    public void move(Figure figure, Game game, User user, Move move) {

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
