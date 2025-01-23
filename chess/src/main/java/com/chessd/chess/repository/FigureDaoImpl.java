package com.chessd.chess.repository;

import com.chessd.chess.entity.Game;
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
        Figure managedFigure = entityManager.find(Figure.class, figure.getId());
        if (managedFigure != null) {
            try {
                entityManager.remove(managedFigure);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else{
            throw new IllegalArgumentException("Figure with Id" + figure.getId() + " does not exist");
        }
    }

    @Override
    @Transactional
    public void update(Figure figure) {
        entityManager.merge(figure);
    }
}
