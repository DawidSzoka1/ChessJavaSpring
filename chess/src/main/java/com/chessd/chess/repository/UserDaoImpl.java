package com.chessd.chess.repository;

import com.chessd.chess.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao{

    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findByUserName(String username) {
        TypedQuery<User> query = entityManager.createQuery("FROM  User where userName=:uName and enable=true", User.class);
        query.setParameter("uName", username);
        return query.getSingleResult();
    }

//    @Override
//    public User findByEmail(String email) {
//        TypedQuery<User> query = entityManager.createQuery("FROM  User where email=:email and enable=true", User.class);
//        query.setParameter("email", email);
//        return query.getSingleResult();
//    }

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
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("FROM  User where enable=true", User.class);

        return query.getResultList();
    }

    @Override
    public List<User> findAllSortedByNameASC() {
        TypedQuery<User> query = entityManager.createQuery("FROM  User where enable=true order by userName asc", User.class);
        return query.getResultList();
    }

    @Override
    public Optional<User> findById(int id) {
        try{
            TypedQuery<User> query = entityManager.createQuery("FROM  User where id=:id and enable=true", User.class);
            query.setParameter("id", id);
            return Optional.ofNullable(query.getSingleResult());
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
