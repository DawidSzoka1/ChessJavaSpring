package com.chessd.chess.game.repository;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.utils.GameResult;
import com.chessd.chess.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameDao extends JpaRepository<Game, String> {
    List<Game> getGamesByWhiteOrBlack(User white, User black);

    Page<Game> findAllByResult(GameResult result, Pageable pageable);

    Page<Game> findAllByResultIsNot(GameResult result, Pageable pageable);

    Page<Game> findAllByWhiteOrBlack(User white, User black, Pageable pageable);
}