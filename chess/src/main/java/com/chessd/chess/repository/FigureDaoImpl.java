package com.chessd.chess.repository;

import com.chessd.chess.utils.Figure;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FigureDaoImpl implements FigureDao{
    private EntityManager entityManager;

    @Autowired
    public FigureDaoImpl(EntityManager entityManager){
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
        entityManager.remove(figure);
    }

    @Override
    @Transactional
    public void update(Figure figure) {
        entityManager.merge(figure);
    }
}
