package com.chessd.chess.game.repository;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.utils.Position;
import com.chessd.chess.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface GameDao extends JpaRepository<Game, String> {
    List<Game> getGamesByWhiteOrBlack(User white, User black);
}