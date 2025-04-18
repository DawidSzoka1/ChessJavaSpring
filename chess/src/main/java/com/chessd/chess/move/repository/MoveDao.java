package com.chessd.chess.move.repository;

import com.chessd.chess.move.entity.Move;

import java.util.List;

public interface MoveDao {
    List<Move> getMovesByGameId(String gameId);

    List<Move> getMovesByGameIdAndPlayerId(String gameId, String playerId);

    void save(Move move);

    void update(Move move);

    void delete(Move move);
}
