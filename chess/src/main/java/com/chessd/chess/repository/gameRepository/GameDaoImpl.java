package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.gameEntity.Game;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        return null;
    }

    @Override
    public List<Game> getGamesByPlayerId(String playerId) {
        return List.of();
    }

    @Override
    public List<Game> getAllGames() {
        return List.of();
    }

    @Override
    public void save(Game game) {

    }


    @Override
    public void update(Game game) {

    }
}
