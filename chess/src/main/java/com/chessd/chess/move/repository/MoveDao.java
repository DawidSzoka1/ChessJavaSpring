package com.chessd.chess.move.repository;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.move.entity.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MoveDao  extends JpaRepository<Move, String> {

    List<Move> getAllByGame(Game game);

    Move findByMoveId(String moveId);
}
