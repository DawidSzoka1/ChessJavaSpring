package com.chessd.chess.game.repository;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.game.utils.GameResult;
import com.chessd.chess.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameDao extends JpaRepository<Game, String> {
    List<Game> getGamesByWhiteOrBlack(User white, User black);

    Page<Game> findAllByResult(GameResult result, Pageable pageable);

    Page<Game> findAllByResultIsNot(GameResult result, Pageable pageable);

    Page<Game> findAllByWhiteOrBlack(User white, User black, Pageable pageable);

    List<Game> getGamesByWinner(User user);

    @Query("select g from Game g where g.result =:result and (g.black =:user or g.white=:user)")
    List<Game> getGamesByWhiteOrBlackAndResult(
            @Param("result") GameResult gameResult,
            @Param("user") User user);

    @Query("select g from Game g where g.winner is not null and g.winner <>:user and (g.white =:user1 or g.black =:user2)")
    List<Game> getGamesByWinnerNotAndWinnerNotNullAndWhiteOrBlack(User user, User user1, User user2);

    List<Game> findAllByGameType(GameType gameType);
}