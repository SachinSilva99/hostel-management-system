package com.sachin.hostelmanagementsystem.service.custom;

import com.sachin.hostelmanagementsystem.dto.ReservationDTO;
import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.service.SuperService;

public interface ReservationService extends SuperService {
    void proceedReservation(StudentDTO studentDTO, ReservationDTO reservationDTO);
}
