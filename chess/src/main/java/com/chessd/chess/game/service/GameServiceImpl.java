package com.chessd.chess.game.service;

import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.entity.Pawn;
import com.chessd.chess.figure.utils.Column;
import com.chessd.chess.figure.utils.CreatingFigures;
import com.chessd.chess.figure.utils.Position;
import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.event.ValidateMoveEvent;
import com.chessd.chess.figure.repository.FigureDao;
import com.chessd.chess.game.repository.GameDao;
import com.chessd.chess.game.utils.GameResult;
import com.chessd.chess.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            figureDao.save(new Pawn("W",
                    Position.fromColumnRow(colName, 1).orElseThrow(),
                    game,
                    List.of(new String[]{Position.fromColumnRow(colName, 2).get().toString(),
                            Position.fromColumnRow(colName, 3).get().toString()})
            ));
            figureDao.save(new Pawn("B", Position.fromColumnRow(colName, 6).orElseThrow(), game,
                    List.of(new String[]{Position.fromColumnRow(colName, 5).get().toString(),
                            Position.fromColumnRow(colName, 4).get().toString()})));

            //Putting other figures on a1, b1, ..., h1 and a8, b8, ..., h8
            figureDao.save(CreatingFigures.putFigure(this.figuresName[i], "W", Position.fromColumnRow(colName, 0).orElseThrow(), game));
            figureDao.save(CreatingFigures.putFigure(this.figuresName[i], "B", Position.fromColumnRow(colName, 7).orElseThrow(), game));
        }
    }

    public Game getGameIfExists(String gameId) {
        Optional<Game> gameOpt = gameDao.findById(gameId);
        //TODO throw error custom
        return gameOpt.orElse(null);
    }

    @Override
    public void move(String gameId, String from, String to, String color, boolean take) throws Exception {
        Game game = this.getGameIfExists(gameId);
        System.out.println("Status gry: " + game.getResult().name());
        if (!game.getResult().name().equals("ONGOING")) {
            throw new Exception("Game is over");
        }
        Figure figure = figureDao.getFigureByPosition(Position.fromString(from).orElseThrow(), game);
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
        gameDao.save(game);
    }

    @Override
    public Page<Game> getGamesByPlayer(int pageNumber, int pageSize, User user) {
        Sort sort = Sort.by(Sort.Order.desc("start"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return gameDao.findAllByWhiteOrBlack(user, user, pageable);
    }

    @Override
    public Page<Game> getAllGames(int pageNumber, int pageSize) {
        Sort sort = Sort.by(Sort.Order.desc("start"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return gameDao.findAll(pageable);
    }

    @Override
    public Page<Game> getAllLiveGames(int pageNumber, int pageSize) {
        Sort sort = Sort.by(Sort.Order.desc("start"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return gameDao.findAllByResult(GameResult.ONGOING, pageable);
    }

    @Override
    public Page<Game> getAllEndedGames(int pageNumber, int pageSize) {
        Sort sort = Sort.by(Sort.Order.desc("start"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return gameDao.findAllByResultIsNot(GameResult.ONGOING, pageable);
    }

    @Override
    public Optional<Game> getGameById(String gameId) {
        return gameDao.findById(gameId);
    }

    @Override
    public void save(Game game) {
        gameDao.save(game);
    }

    @Override
    public void update(Game game) {
        gameDao.save(game);
    }

    @Override
    public void delete(Game game) {
        gameDao.delete(game);
    }

    @Override
    public HashMap<Position, Figure> getBoard(Game game) {
        HashMap<Position, Figure> board = new HashMap<>();
        for (Figure f : figureDao.getAllFigureByGame(game)) {
            board.put(f.getPosition(), f);
        }
        return board;
    }

    @Override
    public Figure[][] getBoardAsTable(Game game) {
        Figure[][] board = new Figure[8][8];
        for (Figure f : figureDao.getAllFigureByGame(game)) {
            board[f.getRow()][f.getCol()] = f;
        }
        return board;
    }
}
