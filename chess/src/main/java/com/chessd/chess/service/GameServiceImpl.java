package com.chessd.chess.service;

import com.chessd.chess.entity.User;
import com.chessd.chess.entity.gameEntity.Game;
import com.chessd.chess.entity.gameEntity.Move;
import com.chessd.chess.repository.gameRepository.FigureDao;
import com.chessd.chess.repository.gameRepository.GameDao;
import com.chessd.chess.repository.gameRepository.MoveDao;
import com.chessd.chess.utils.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chessd.chess.utils.Figure;


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


    @Override
    public void startGame(Game game) {
        Map<Integer, String> pieceMapping = Map.of(
                0, "Wr",
                1, "Wk",
                2, "Wb",
                3, "Wq",
                4, "Wk",
                5, "Wb",
                6, "Wk",
                7, "Wr"
        );

        for (Column column : Column.values()) {

        }

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
