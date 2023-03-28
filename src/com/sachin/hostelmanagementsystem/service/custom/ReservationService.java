package com.sachin.hostelmanagementsystem.service.custom;

import com.sachin.hostelmanagementsystem.dto.ReservationDTO;
import com.sachin.hostelmanagementsystem.dto.StudentDTO;

public interface ReservationService {
    boolean proceedReservation(StudentDTO studentDTO, ReservationDTO reservationDTO);
}
