package com.chessd.chess.repository;

import com.chessd.chess.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class RoleDaoImpl implements RoleDao {
    private EntityManager entityManager;

    @Autowired
    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findByRole(String role) {
        TypedQuery<Role> query = entityManager.createQuery("FROM Role where name=:role", Role.class);
        query.setParameter("role", role);
        return query.getSingleResult();
    }
}
