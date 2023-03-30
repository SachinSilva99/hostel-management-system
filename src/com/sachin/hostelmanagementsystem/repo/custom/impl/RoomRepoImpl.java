package com.sachin.hostelmanagementsystem.repo.custom.impl;

import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.constants.ROOM_TYPE;
import com.sachin.hostelmanagementsystem.repo.custom.RoomRepo;
import com.sachin.hostelmanagementsystem.repo.exception.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
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
        return rooms;
    }

    @Override
    public Optional<Room> findByPk(String pk, Session session) {
        Room room = session.get(Room.class, pk);
        return room == null ? Optional.empty() : Optional.of(room);
    }

    @Override
    public boolean existByPk(String pk, Session session) {
        Room room = session.get(Room.class, pk);
        return room != null;
    }

    @Override
    public long count(Session session) {
        Query query = session.createQuery("select count(*) from Room");
        return (Long) query.uniqueResult();
    }

    @Override
    public long getAvailableRoomsCount(Session session) {
        Query query = session.createQuery("select sum(r.qty) from Room r");
        return query.uniqueResult() == null ? 0 : (Long) query.uniqueResult();
    }

    @Override
    public long getRoomCountForType(ROOM_TYPE roomType, Session session) {
        Query query = session.createQuery("select sum(r.qty) from Room r where r.roomType = :roomType");
        query.setParameter("roomType", roomType);

        return query.uniqueResult() == null ? 0 : (Long) query.uniqueResult();
    }

    @Override
    public List<String> getRoomIds(ROOM_TYPE roomType, Session session) {
        Query query = session.createQuery("select r.room_type_id from Room r where r.roomType = :roomType");
        query.setParameter("roomType", roomType);
        List<String> ids =  query.list();
        return ids == null ? new ArrayList<String>() : ids;
    }
}
