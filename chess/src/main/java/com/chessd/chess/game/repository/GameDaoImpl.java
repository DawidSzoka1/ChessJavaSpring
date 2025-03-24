package com.chessd.chess.game.repository;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.utils.Position;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
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
                        "order by start", Game.class);
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

    @Override
    public HashMap<Position, Figure> getBoard(Game game) {
        TypedQuery<Figure> query = entityManager.createQuery("SELECT f from Figure f where f.game =:game", Figure.class);
        query.setParameter("game", game);
        HashMap<Position, Figure> board = new HashMap<>();
        for(Figure f: query.getResultList()){
            board.put(f.getPosition(), f);
        }
        return board;
    }

    @Override
    public Figure[][] getBoardAsTable(Game game) {
        TypedQuery<Figure> query = entityManager.createQuery("SELECT f from Figure f where f.game =:game", Figure.class);
        query.setParameter("game", game);
        Figure[][] board = new Figure[8][8];
        for (Figure figure : query.getResultList()) {
            board[figure.getRow()][figure.getCol()] = figure;
        }
        return board;
    }
}
