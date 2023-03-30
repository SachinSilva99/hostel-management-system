package com.sachin.hostelmanagementsystem.repo.custom;

import com.sachin.hostelmanagementsystem.entity.Reservation;
import com.sachin.hostelmanagementsystem.repo.CrudRepo;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public interface ReservationRepo extends CrudRepo<Reservation, String> {
    List<String> getPendingReservations(Session session);
}
