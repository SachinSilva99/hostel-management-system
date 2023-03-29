package com.sachin.hostelmanagementsystem.service.custom.impl;

import com.sachin.hostelmanagementsystem.dto.ReservationDTO;
import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.entity.Reservation;
import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.Student;
import com.sachin.hostelmanagementsystem.repo.RepoFactory;
import com.sachin.hostelmanagementsystem.repo.RepoType;
import com.sachin.hostelmanagementsystem.repo.custom.ReservationRepo;
import com.sachin.hostelmanagementsystem.repo.custom.RoomRepo;
import com.sachin.hostelmanagementsystem.repo.custom.StudentRepo;
import com.sachin.hostelmanagementsystem.service.custom.ReservationService;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import com.sachin.hostelmanagementsystem.service.exception.ReservationFailedException;
import com.sachin.hostelmanagementsystem.util.FactoryConfiguration;
import com.sachin.hostelmanagementsystem.util.Mapper;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationServiceImpl implements ReservationService {
    private final RoomRepo roomRepo = RepoFactory.getInstance().getRepo(RepoType.ROOM);
    private final StudentRepo studentRepo = RepoFactory.getInstance().getRepo(RepoType.STUDENT);
    private final ReservationRepo reservationRepo = RepoFactory.getInstance().getRepo(RepoType.RESERVATION);
    private final Mapper mapper = new Mapper();

    @Override
    public void proceedReservation(StudentDTO studentDTO, ReservationDTO reservationDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();

        Student student = mapper.toStudent(studentDTO);
        Reservation reservation = mapper.toReservation(reservationDTO);

        Transaction transaction = session.beginTransaction();
        List<Reservation> reservations = new ArrayList<>();
        try {

            student.setReservations(reservations);
            reservation.setStudent(student);
            Optional<Room> roomByPk = roomRepo.findByPk(reservationDTO.getRoomTypeId(), session);
            if (!roomByPk.isPresent()) {
                throw new NotFoundException(reservationDTO.getRoomTypeId() + " Not found");
            }
            Room room = roomByPk.get();
            reservation.setRoom(room);
            //Save
            room.setQty(room.getQty() - 1);
            studentRepo.save(student, session);
            reservationRepo.save(reservation, session);
            roomRepo.update(room, session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new ReservationFailedException("Reservation Failed");
        } finally {
            session.close();
        }

    }
}
