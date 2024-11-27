package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.gameEntity.Move;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MoveDaoImpl implements MoveDao {
    private EntityManager entityManager;

    @Autowired
    public MoveDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Move> getMovesByGameId(String gameId) {
        return List.of();
    }

    @Override
    public List<Move> getMovesByGameIdAndPlayerId(String gameId, String playerId) {
        return List.of();
    }

    @Override
    public void save(Move move) {

    }

    @Override
    public void update(Move move) {

    }
}
