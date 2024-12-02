package com.chessd.chess.service;

import com.chessd.chess.entity.User;
import com.chessd.chess.entity.gameEntity.Figure;
import com.chessd.chess.entity.gameEntity.Game;
import com.chessd.chess.entity.gameEntity.Move;
import com.chessd.chess.repository.gameRepository.FigureDao;
import com.chessd.chess.repository.gameRepository.GameDao;
import com.chessd.chess.repository.gameRepository.MoveDao;
import com.chessd.chess.utils.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class GameServiceImpl implements GameService {
    private GameDao gameDao;
    private FigureDao figureDao;
    private MoveDao moveDao;

    @Autowired
    public GameServiceImpl(GameDao gameDao, FigureDao figureDao, MoveDao moveDao) {
        this.gameDao = gameDao;
        this.figureDao = figureDao;
        this.moveDao = moveDao;
    }


    @Override
    public void startGame(Game game) {
        Map<Integer, String> pieceMapping = Map.of(
                0, "Ww",
                1, "Ws",
                2, "Wg",
                3, "Wh",
                4, "Wk",
                5, "Wg",
                6, "Ws",
                7, "Ww"
        );
        for (Column column : Column.values()) {
            figureDao.save(new Figure(game, "Wp", column + "2"));
            figureDao.save(new Figure(game, "Bp", column + "7"));
            if (pieceMapping.containsKey(column.getIndex())) {
                figureDao.save(new Figure(
                        game,
                        pieceMapping.get(column.getIndex()),
                        column + "1"));
                figureDao.save(new Figure(
                        game,
                        pieceMapping.get(column.getIndex()).replace("W", "B"),
                        column + "8"));
            }
        }

    }

    @Override
    public void move(Figure figure, Game game, User user, Move move) {

    }

    @Override
    public void endGame(Game game) {

    }
}
