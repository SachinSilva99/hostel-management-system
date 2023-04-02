package com.sachin.hostelmanagementsystem.util;

import com.sachin.hostelmanagementsystem.dto.ReservationDTO;
import com.sachin.hostelmanagementsystem.dto.RoomDTO;
import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.entity.Reservation;
import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.Student;

public class Mapper {
    public Student toStudent(StudentDTO dto) {
        return new Student(
                dto.getStudent_id(),
                dto.getName(),
                dto.getAddress(),
                dto.getContact_no(),
                dto.getDob(),
                dto.getGender()
        );
    }

    public StudentDTO toStudentDto(Student student) {
        return new StudentDTO(
                student.getStudent_id(),
                student.getName(),
                student.getAddress(),
                student.getContact_no(),
                student.getDob(),
                student.getGender()
        );
    }

    public Room toRoom(RoomDTO dto) {
        return new Room(
                dto.getRoom_type_id(),
                dto.getRoomType(),
                dto.getKey_money(),
                dto.getQty()
        );
    }

    public RoomDTO toRoomDto(Room room) {
        return new RoomDTO(
                room.getRoom_type_id(),
                room.getRoomType(),
                room.getKey_money(),
                room.getQty()
        );
    }

    public Reservation toReservation(ReservationDTO dto) {
        return new Reservation(
                dto.getRes_id(),
                dto.getDate(),
                dto.getStatus()
        );
    }

    public ReservationDTO toReservationDto(Reservation reservation, StudentDTO studentDTO) {
        return new ReservationDTO(
                reservation.getRes_id(),
                reservation.getDate(),
                reservation.getStatus(),
                reservation.getRoom().getRoom_type_id(),
                toStudentDto(reservation.getStudent())
        );
    }
}
