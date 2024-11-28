package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.gameEntity.Game;

import java.util.List;

public interface GameDao {
    Game getGameById(String gameId);
    List<Game> getGamesByPlayerId(String playerId);
    List<Game> getAllGames();
    void save(Game game);
    void update(Game game);
    void delete(Game game);
}
