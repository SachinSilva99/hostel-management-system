package com.sachin.hostelmanagementsystem.repo.custom.impl;

import com.sachin.hostelmanagementsystem.entity.Reservation;
import com.sachin.hostelmanagementsystem.entity.constants.STATUS;
import com.sachin.hostelmanagementsystem.repo.custom.ReservationRepo;
import com.sachin.hostelmanagementsystem.repo.exception.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public class ReservationRepoImpl implements ReservationRepo {
    @Override
    public Reservation save(Reservation reservation, Session session) throws ConstraintViolationException {
        try {
            String res_id = (String) session.save(reservation);
            return session.get(Reservation.class, res_id);
        } catch (Exception e) {
            throw new ConstraintViolationException("Reservation did not save!");
        }
    }

    @Override
    public Reservation update(Reservation reservation, Session session) throws ConstraintViolationException {
        try {
            session.update(reservation);
            return reservation;
        } catch (Exception e) {
            throw new ConstraintViolationException("Reservation did not update");
        }
    }

    @Override
    public void delete(Reservation reservation, Session session) throws ConstraintViolationException {
        try {
            session.delete(reservation);
        } catch (Exception e) {
            throw new ConstraintViolationException("Reservation did not delete");
        }
    }

    @Override
    public List<Reservation> findAll(Session session) {
        CriteriaQuery<Reservation> query = session.getCriteriaBuilder().createQuery(Reservation.class);
        query.from(Reservation.class);
        List<Reservation> reservations = session.createQuery(query).getResultList();
        session.close();
        return reservations;
    }

    @Override
    public Optional<Reservation> findByPk(String pk, Session session) {
        Reservation reservation = session.get(Reservation.class, pk);
        return reservation == null ? Optional.empty() : Optional.of(reservation);
    }

    @Override
    public boolean existByPk(String pk, Session session) {
        Reservation reservation = session.get(Reservation.class, pk);
        return reservation != null;
    }

    @Override
    public long count(Session session) {
        Query query = session.createQuery("select count(*) from Reservation ");
        return (Long) query.uniqueResult();
    }

    @Override
    public List<Reservation> search(String text, Session session) {
        String hql = "FROM Reservation R WHERE R.id = :text or R.student.student_id = :text or R.room.room_type_id = :text";
        Query query = session.createQuery(hql);
        query.setParameter("text", text);
        return query.list();
    }

    @Override
    public List<String> getReservations(Session session, STATUS status) {
        String hql = "select R.res_id FROM Reservation R WHERE R.status = :status";
        Query query = session.createQuery(hql);
        query.setParameter("status", status);
        return query.list();
    }

    @Override
    public String getLastResId(Session session) {
        String hql = "select R.res_id FROM Reservation R order by R.res_id desc";
        Query query = session.createQuery(hql);
        query.setMaxResults(1);
        if(query.uniqueResult() == null)return null;
        return(String) query.uniqueResult();
    }
}
