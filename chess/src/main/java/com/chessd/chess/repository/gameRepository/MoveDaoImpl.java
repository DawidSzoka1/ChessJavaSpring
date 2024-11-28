package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.gameEntity.Move;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        TypedQuery<Move> query = entityManager.createQuery(
                "FROM Move where game.gameId=:game order by time",
                Move.class
        ).setParameter("game", gameId);

        return query.getResultList();
    }

    @Override
    public List<Move> getMovesByGameIdAndPlayerId(String gameId, String playerId) {
        TypedQuery<Move> query = entityManager.createQuery(
                        "FROM Move where game.gameId=:game and user.id=:playerId order by time",
                        Move.class
                ).setParameter("game", gameId)
                .setParameter("playerId", playerId);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Move move) {
        entityManager.persist(move);
    }

    @Override
    @Transactional
    public void update(Move move) {
        entityManager.merge(move);
    }

    @Override
    @Transactional
    public void delete(Move move) {
        entityManager.remove(move);
    }
}
