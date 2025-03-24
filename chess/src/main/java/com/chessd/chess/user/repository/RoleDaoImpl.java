package com.chessd.chess.user.repository;

import com.chessd.chess.user.entity.Role;
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
    public Role findByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("FROM Role where name=:name", Role.class);
        query.setParameter("name", name);
        Role role;
        try {
            role = query.getSingleResult();
        }catch (Exception _){
            throw new RuntimeException("Given role dont exist: " + name);
        }
        return role;
    }
}
