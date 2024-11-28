package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.gameEntity.Game;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class GameDaoImpl implements GameDao {
    private EntityManager entityManager;

    @Autowired
    public GameDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Game getGameById(String gameId) {
        TypedQuery<Game> query = entityManager.createQuery(
                "SELECT g FROM Game g WHERE g.gameId = :gameId", Game.class);
        query.setParameter("gameId", gameId);
        return query.getSingleResult();
    }

    @Override
    public List<Game> getGamesByPlayerId(String playerId) {
        TypedQuery<Game> query = entityManager.createQuery(
                "SELECT g FROM Game g WHERE g.white = :playerId or g.black = :playerId " +
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
