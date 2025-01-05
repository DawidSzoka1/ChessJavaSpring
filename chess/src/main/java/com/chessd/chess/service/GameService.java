package com.chessd.chess.service;

import com.chessd.chess.utils.Figure;
import com.chessd.chess.entity.gameEntity.Game;
import java.util.Optional;

public interface GameService {
    void startGame(Game game);
    boolean move(String gameId, String from, String to, String color, Figure[][] lastBoard);
    void endGame(Game game);
    Optional<Game> getGameById(String gameId);
    Optional<Figure> getFigureById(String position, String gameId);
}
