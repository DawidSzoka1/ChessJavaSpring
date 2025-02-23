package com.chessd.chess.service;

import com.chessd.chess.entity.figureEntity.*;
import com.chessd.chess.entity.Game;
import com.chessd.chess.event.ValidateMoveEvent;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.repository.gameRepository.GameDao;
import com.chessd.chess.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.jetbrains.annotations.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

/**
 * Service implementation for managing chess game logic, including starting a game,
 * processing moves, and interacting with the game board.
 */
@Service
public class GameServiceImpl implements GameService {
    private GameDao gameDao;
    private FigureDao figureDao;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final String[] figuresName = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};

    @Autowired
    public GameServiceImpl(GameDao gameDao, FigureDao figureDao, ApplicationEventPublisher applicationEventPublisher) {
        this.gameDao = gameDao;
        this.figureDao = figureDao;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Initializes the chess board by placing figures in their starting positions.
     * This method sets up the pawns and other pieces for both white and black players.
     *
     * @param game The {@link Game} to initialize.
     */
    @Override
    public void startGame(@NotNull Game game) {
        for (int i = 0; i < 8; i++) {
            Column colName = Column.fromIndex(i).orElseThrow();

            //Putting pawns on a2, b2, ..., h2 and a7, b7, ..., h7
            figureDao.save(new Pawn("W", Position.fromColumnRow(colName, 1).get(), game));
            figureDao.save(new Pawn("B", Position.fromColumnRow(colName, 6).get(), game));

            //Putting other figures on a1, b1, ..., h1 and a8, b8, ..., h8
            figureDao.save(CreatingFigures.putFigure(this.figuresName[i], "W", Position.fromColumnRow(colName, 0).get(), game));
            figureDao.save(CreatingFigures.putFigure(this.figuresName[i], "B", Position.fromColumnRow(colName, 7).get(), game));
        }
    }

    public Game getGameIfExists(String gameId){
        Optional<Game> gameOpt = gameDao.getGameById(gameId);
        //TODO throw error custom
        return gameOpt.orElse(null);
    }

    @Override
    public void move(String gameId, String from, String to, String color, boolean take) throws Exception {
        Game game = this.getGameIfExists(gameId);
        System.out.println("Status gry: " + game.getResult().name());
        if(!game.getResult().name().equals("ONGOING")){
            throw new Exception("Game is over");
        }
        Figure figure = figureDao.getFigureByPosition(from, game);
        applicationEventPublisher.publishEvent(
                new ValidateMoveEvent(this,
                        figure,
                        from,
                        to,
                        game,
                        this.getBoard(game),
                        take ? "take" : "move"
                )
        );
    }

    @Override
    public void endGame(Game game, GameResult result) {
        game.setEnd(Timestamp.from(Instant.now()));
        game.setResult(result);
        gameDao.update(game);
    }

    @Override
    public Optional<Game> getGameById(String gameId) {
        return gameDao.getGameById(gameId);
    }

    @Override
    public void save(Game game) {
        gameDao.save(game);
    }

    @Override
    public void update(Game game) {
        gameDao.update(game);
    }

    @Override
    public void delete(Game game) {
        gameDao.delete(game);
    }

    @Override
    public HashMap<Position, Figure> getBoard(Game game) {
        return gameDao.getBoard(game);
    }

    @Override
    public Figure[][] getBoardAsTable(Game game) {
        return gameDao.getBoardAsTable(game);
    }
}
