package com.chessd.chess.move.repository;

import com.chessd.chess.move.entity.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MoveDao extends JpaRepository<Move, String> {

    Move findByMoveId(String moveId);

    List<Move> findAllByGameId_GameId_OrderByTimeDesc(String gameId);
}
