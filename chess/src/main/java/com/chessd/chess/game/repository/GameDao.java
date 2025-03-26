package com.chessd.chess.game.repository;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.utils.Position;
import com.chessd.chess.user.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface GameDao {
    Optional<Game> getGameByGameId(String gameId);
    List<Game> getGamesByPlayer(User user);
    List<Game> getAllGames();
    List<Game> getGamesWithPagination(int page, int size, User user);
    void save(Game game);
    void update(Game game);
    void delete(Game game);
    HashMap<Position, Figure> getBoard(Game game);
    Figure[][] getBoardAsTable(Game game);
}