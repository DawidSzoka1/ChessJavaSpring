package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameDao {
    Optional<Game> getGameById(String gameId);
    List<Game> getGamesByPlayerId(String playerId);
    List<Game> getAllGames();
    void save(Game game);
    void update(Game game);
    void delete(Game game);
}
