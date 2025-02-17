package com.chessd.chess.repository;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class FigureDaoImpl implements FigureDao {
    private EntityManager entityManager;

    @Autowired
    public FigureDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void save(Figure figure) {
        entityManager.persist(figure);
    }

    @Override
    @Transactional
    public void delete(Figure figure) {
        Figure managedFigure = entityManager.find(Figure.class, figure.getId());
        if (managedFigure != null) {
            try {
                entityManager.remove(managedFigure);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Figure with Id" + figure.getId() + " does not exist");
        }
    }

    @Override
    @Transactional
    public void update(Figure figure) {
        entityManager.merge(figure);
    }

    @Override
    public Optional<Figure> getFigureByPossibleMovesAndColor(Game game, String color, String move) {
        TypedQuery<Figure> query = entityManager
                .createQuery("SELECT f FROM Figure f Join f.moves m where f.game=:game and f.color=:color and m in (:move)",
                        Figure.class);
        query.setParameter("game", game);
        query.setParameter("color", color);
        List<String> moves = List.of(move);
        query.setParameter("move", moves);
        List<Figure> figures = query.getResultList();
        if(figures.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(figures.getFirst());
    }
    @Override
    public Figure getKing(Game game, String color) {
        TypedQuery<Figure> query = entityManager.createQuery(
                "select f from Figure f where f.game=:game and f.name='king' and lower(f.color) =:color",
                Figure.class);
        query.setParameter("game", game);
        query.setParameter("color", color.toLowerCase());
        return query.getSingleResult();
    }
}
