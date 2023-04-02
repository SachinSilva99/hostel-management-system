package com.sachin.hostelmanagementsystem.service.custom;

import com.sachin.hostelmanagementsystem.dto.ReservationDTO;
import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.entity.constants.STATUS;
import com.sachin.hostelmanagementsystem.service.SuperService;

import java.util.List;

public interface ReservationService extends SuperService {
    void proceedReservation(ReservationDTO reservationDTO);
    ReservationDTO getReservationDTO(String res_id);
    List<String> getReservations(STATUS status);

    ReservationDTO update(String selectedItem, STATUS status);

    ReservationDTO getReservation(String res_id);
    String generateResId(String currentResId);

}
