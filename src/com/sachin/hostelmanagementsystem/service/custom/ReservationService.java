package com.sachin.hostelmanagementsystem.service.custom;

import com.sachin.hostelmanagementsystem.dto.ReservationDTO;
import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.entity.constants.STATUS;
import com.sachin.hostelmanagementsystem.service.SuperService;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import com.sachin.hostelmanagementsystem.service.exception.ReservationFailedException;

import java.util.List;

public interface ReservationService extends SuperService {
    void proceedReservation(ReservationDTO reservationDTO) throws ReservationFailedException;
    ReservationDTO getReservationDTO(String res_id) throws NotFoundException;
    List<String> getReservations(STATUS status);

    ReservationDTO update(String selectedItem, STATUS status) throws NotFoundException, ReservationFailedException;

    ReservationDTO getReservation(String res_id);
    String generateResId(String currentResId);

}
