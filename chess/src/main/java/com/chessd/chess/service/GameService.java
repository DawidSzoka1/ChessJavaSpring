package com.chessd.chess.service;

import com.chessd.chess.entity.User;
import com.chessd.chess.entity.gameEntity.Figure;
import com.chessd.chess.entity.gameEntity.Game;
import com.chessd.chess.entity.gameEntity.Move;

import java.util.Optional;

public interface GameService {
    void startGame(Game game);
    void move(Figure figure, Game game, User user, Move move);
    void endGame(Game game);
    Optional<Game> getGameById(String gameId);
    Optional<Figure> getFigureById(String position, String gameId);


}
