package com.chessd.chess.user.repository;

import com.chessd.chess.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private User userOrNull(TypedQuery<User> query) {
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception _) {
        }
        return user;
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findByUserName(String username) {
        TypedQuery<User> query = entityManager
                .createQuery("FROM  User where userName=:uName and enable=true", User.class);
        query.setParameter("uName", username);
        return this.userOrNull(query);
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager
                .createQuery("FROM User where email=:email and enable=true", User.class);
        query.setParameter("email", email);
        return this.userOrNull(query);
    }

    @Override
    @Transactional
    public void delete(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public List<User> findALlByRanking() {
        TypedQuery<User> query = entityManager.createQuery(
                "From User where enable=true and isGuest=false order by ranking desc", User.class
        );
        return query.getResultList();
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager
                .createQuery("FROM  User where enable=true and isGuest=false", User.class);

        return query.getResultList();
    }

    @Override
    public List<User> findAllSortedByNameASC() {
        TypedQuery<User> query = entityManager
                .createQuery(
                        "FROM  User where enable=true and isGuest=false order by userName asc",
                        User.class);
        return query.getResultList();
    }

    @Override
    public Optional<User> findById(int id) {
        try {
            TypedQuery<User> query = entityManager.createQuery("FROM  User where id=:id and enable=true", User.class);
            query.setParameter("id", id);
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
