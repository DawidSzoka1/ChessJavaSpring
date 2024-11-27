package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.User;
import com.chessd.chess.entity.gameEntity.Figure;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FigureDaoImpl implements FigureDao {
    private EntityManager entityManager;

    @Autowired
    public FigureDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Figure getFigureByPosition(String position) {
        return null;
    }

    @Override
    public List<Figure> getAllFigures() {
        return List.of();
    }


    @Override
    public Figure getFigureByName(String figureName) {
        return null;
    }

    @Override
    public void save(Figure figure) {

    }

    @Override
    public void delete(Figure figure) {

    }

    @Override
    public void update(Figure figure) {

    }

    @Override
    public void moveToPosition(Figure figure, String position) {

    }
}
