package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.gameEntity.Game;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class GameDaoImpl implements GameDao {
    private EntityManager entityManager;

    @Autowired
    public GameDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Game> getGameById(String gameId) {
        try {
            TypedQuery<Game> query = entityManager.createQuery(
                    "FROM Game  WHERE gameId =:gameId", Game.class);
            query.setParameter("gameId", gameId);

            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Game> getGamesByPlayerId(String playerId) {
        TypedQuery<Game> query = entityManager.createQuery(
                "FROM Game WHERE white = :playerId or black = :playerId " +
                        "order by g.start", Game.class);
        query.setParameter("playerId", playerId);
        return query.getResultList();
    }

    @Override
    public List<Game> getAllGames() {
        TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Game g", Game.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Game game) {
        entityManager.persist(game);
    }


    @Override
    @Transactional
    public void update(Game game) {
        entityManager.merge(game);
    }


    @Override
    @Transactional
    public void delete(Game game) {
        entityManager.remove(game);
    }
}
