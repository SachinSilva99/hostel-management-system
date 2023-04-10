package com.sachin.hostelmanagementsystem.service.custom.impl;

import com.sachin.hostelmanagementsystem.dto.ReservationDTO;
import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.entity.Reservation;
import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.Student;
import com.sachin.hostelmanagementsystem.entity.constants.STATUS;
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
    public void proceedReservation(ReservationDTO reservationDTO) throws ReservationFailedException {
        Session session = FactoryConfiguration.getInstance().getSession();

        Student student = mapper.toStudent(reservationDTO.getStudentDTO());
        Reservation reservation = mapper.toReservation(reservationDTO);

        Transaction transaction = session.beginTransaction();
        try {
            student.getReservations().add(reservation);
            reservation.setStudent(student);
            Optional<Room> roomByPk = roomRepo.findByPk(reservationDTO.getRoomTypeId(), session);
            if (!roomByPk.isPresent()) {
                throw new NotFoundException(reservationDTO.getRoomTypeId() + " Not found");
            }
            Room room = roomByPk.get();
            reservation.setRoom(room);
            room.setQty(room.getQty() - 1);
            //if the student is already in the database, update student
            if (studentRepo.findByPk(student.getStudent_id(), session).isPresent()) {
                Student updatedStudent = studentRepo.findByPk(student.getStudent_id(), session).get();
                updatedStudent.setName(student.getName());
                updatedStudent.setAddress(student.getAddress());
                updatedStudent.setContact_no(student.getContact_no());
                updatedStudent.setDob(student.getDob());
                studentRepo.update(updatedStudent, session);
            } else {
                //Proceed the reservation
                studentRepo.save(student, session);
                reservationRepo.save(reservation, session);
                roomRepo.update(room, session);
                transaction.commit();
            }
        } catch (Exception e) {
            transaction.rollback();
            e.getStackTrace();
            System.out.println(e.getMessage());
            throw new ReservationFailedException();
        } finally {
            session.close();
        }
    }

    @Override
    public ReservationDTO getReservationDTO(String res_id) throws NotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();

        Optional<Reservation> byPk = reservationRepo.findByPk(res_id, session);
        if (!byPk.isPresent()) {
            throw new NotFoundException(byPk + " is not found");
        }
        StudentDTO studentDto = mapper.toStudentDto(byPk.get().getStudent());
        return mapper.toReservationDto(byPk.get(), studentDto);
    }

    @Override
    public List<String> getReservations(STATUS status) {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<String> pendingReservations = reservationRepo.getReservations(session, status);
        session.close();
        return pendingReservations;
    }

    @Override
    public ReservationDTO update(String selectedItem, STATUS status) throws NotFoundException, ReservationFailedException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Optional<Reservation> reservationByPk = reservationRepo.findByPk(selectedItem, session);
            if (!reservationByPk.isPresent()) throw new NotFoundException(selectedItem + " not found to update");

            Reservation reservation = reservationByPk.get();
            reservation.setStatus(status);
            reservationRepo.update(reservation, session);
            String roomTypeId = reservation.getRoom().getRoom_type_id();
            Optional<Room> roomById = roomRepo.findByPk(roomTypeId, session);

            if (!roomById.isPresent()) throw new NotFoundException(roomTypeId + " not found to update");

            Room room = roomById.get();
            room.setQty(room.getQty() + 1);
            roomRepo.update(room, session);
            Reservation updateReservation = reservationRepo.update(reservation, session);
            transaction.commit();
            return mapper.toReservationDto(updateReservation, mapper.toStudentDto(reservation.getStudent()));
        } catch (Exception e) {
            transaction.rollback();
            throw new ReservationFailedException("failed to update reservation");
        } finally {
            session.close();
        }

    }

    @Override
    public ReservationDTO getReservation(String res_id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Optional<Reservation> byPk = reservationRepo.findByPk(res_id, session);
        if (!byPk.isPresent()) {
            return null;
        }
        StudentDTO studentDto = mapper.toStudentDto(byPk.get().getStudent());
        return mapper.toReservationDto(byPk.get(), studentDto);
    }

    @Override
    public String generateResId(String currentResId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        if (currentResId != null) {
            String[] split = currentResId.split("RS00");
            int id = Integer.parseInt(split[1]);
            id += 1;
            return "RS00" + id;
        }
        String lastResId = reservationRepo.getLastResId(session);
        if (lastResId == null) return "RS001";
        return lastResId;
    }
}
