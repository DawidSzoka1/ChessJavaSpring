package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.User;
import com.chessd.chess.entity.gameEntity.Figure;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FigureDaoImpl implements FigureDao {
    private EntityManager entityManager;
    private GameDao gameDao;

    @Autowired
    public FigureDaoImpl(EntityManager entityManager, GameDao gameDao) {
        this.entityManager = entityManager;
        this.gameDao = gameDao;
    }


    @Override
    public Figure getFigureByPosition(String position, String gameId) {
        TypedQuery<Figure> query = entityManager.createQuery(
                "FROM Figure where position=:position and game=:game",
                Figure.class
        );
        query.setParameter("position", position);
        query.setParameter("game", gameDao.getGameById(gameId));
        return query.getSingleResult();
    }

    @Override
    public List<Figure> getAllFigures(String gameId) {
        TypedQuery<Figure> query = entityManager.createQuery(
                "FROM Figure where game=:game",
                Figure.class
        );
        query.setParameter("game", gameDao.getGameById(gameId));
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Figure figure) {
        entityManager.persist(figure);
    }

    @Override
    @Transactional
    public void delete(Figure figure) {
        entityManager.remove(figure);
    }

    @Override
    @Transactional
    public void update(Figure figure) {
        entityManager.merge(figure);
    }

    @Override
    public void moveToPosition(Figure figure, String position) {
        figure.setPosition(position);
    }
}
