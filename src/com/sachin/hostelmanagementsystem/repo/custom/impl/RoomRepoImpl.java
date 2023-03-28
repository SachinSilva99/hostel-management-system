package com.sachin.hostelmanagementsystem.repo.custom.impl;

import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.Student;
import com.sachin.hostelmanagementsystem.repo.custom.RoomRepo;
import com.sachin.hostelmanagementsystem.repo.exception.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public class RoomRepoImpl implements RoomRepo {
    @Override
    public Room save(Room room, Session session) throws ConstraintViolationException {
        try {
            String roomTypeId = (String) session.save(room);
            return session.get(Room.class, roomTypeId);
        } catch (Exception e) {
            throw new ConstraintViolationException("Room did not save!");
        }
    }

    @Override
    public Room update(Room room, Session session) throws ConstraintViolationException {
        try {
            session.update(room);
            return room;
        } catch (Exception e) {
            throw new ConstraintViolationException("Room did not update");
        }
    }

    @Override
    public void delete(Room room, Session session) throws ConstraintViolationException {
        try {
            session.delete(room);
        } catch (Exception e) {
            throw new ConstraintViolationException("Room did not delete");
        }
    }

    @Override
    public List<Room> findAll(Session session) {
        CriteriaQuery<Room> query = session.getCriteriaBuilder().createQuery(Room.class);
        query.from(Room.class);
        List<Room> rooms = session.createQuery(query).getResultList();
        session.close();
        return rooms;
    }

    @Override
    public Optional<Room> findByPk(String pk, Session session) {
        Room room = session.get(Room.class, pk);
        session.close();
        return room == null ? Optional.empty() : Optional.of(room);
    }

    @Override
    public boolean existByPk(String pk, Session session) {
        Room room = session.get(Room.class, pk);
        session.close();
        return room != null;
    }

    @Override
    public long count(Session session) {
        Query query = session.createQuery("select count(*) from Room");
        session.close();
        return (Long) query.uniqueResult();
    }
}
