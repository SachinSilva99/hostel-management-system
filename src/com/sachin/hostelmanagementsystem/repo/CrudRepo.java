package com.sachin.hostelmanagementsystem.repo;

import com.sachin.hostelmanagementsystem.entity.SuperEntity;
import com.sachin.hostelmanagementsystem.repo.exception.ConstraintViolationException;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public interface CrudRepo<T extends SuperEntity, ID > extends SuperRepo{
    T save(T t, Session session) throws ConstraintViolationException;

    T update(T t, Session session)throws ConstraintViolationException;

    void delete(T t, Session session)throws ConstraintViolationException;

    List<T> findAll(Session session) ;

    Optional<T> findByPk(ID pk,Session session);

    boolean existByPk(ID pk,Session session);

    long count(Session session) ;

    List<T> search(String text, Session session);
}
