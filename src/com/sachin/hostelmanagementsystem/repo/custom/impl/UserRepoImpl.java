package com.sachin.hostelmanagementsystem.repo.custom.impl;

import com.sachin.hostelmanagementsystem.entity.User;
import com.sachin.hostelmanagementsystem.repo.custom.UserRepo;
import com.sachin.hostelmanagementsystem.repo.exception.ConstraintViolationException;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
Author : Sachin Silva
*/

public class UserRepoImpl implements UserRepo {
    @Override
    public User save(User user, Session session) throws ConstraintViolationException {
        int id = (Integer) session.save(user);
        user.setId(id);
        return user;
    }

    @Override
    public User update(User user, Session session) throws ConstraintViolationException {
        session.update(user);
        return user;
    }

    @Override
    public void delete(User user, Session session) throws ConstraintViolationException {
        session.delete(user);
    }

    @Override
    public List<User> findAll(Session session) {
        CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(User.class);
        query.from(User.class);
        List<User> users = session.createQuery(query).getResultList();
        return users;
    }

    @Override
    public Optional<User> findByPk(Integer pk, Session session) {
        User user = session.get(User.class, pk);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public boolean existByPk(Integer pk, Session session) {
        User user = session.get(User.class, pk);
        return user != null;
    }

    @Override
    public long count(Session session) {
        Query query = session.createQuery("select count(*) from User ");
        return (Long) query.uniqueResult();
    }

    @Override
    public List<User> search(String text, Session session) {

        CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(User.class);
        query.from(User.class);
        List<User> all = session.createQuery(query).getResultList();
        List<User> users =
                all.stream()
                        .filter(user -> user.getUsername().contains(text))
                        .collect(Collectors.toList());
        return users;
    }

    @Override
    public User getUserByUsername(String username, Session session) throws NotFoundException {
        Query query = session.createQuery("FROM User U where U.username = :username");
        query.setParameter("username", username);
        return (User) query.uniqueResult();
    }
}
