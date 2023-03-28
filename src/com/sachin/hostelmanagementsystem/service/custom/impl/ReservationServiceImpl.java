package com.sachin.hostelmanagementsystem.service.custom.impl;

import com.sachin.hostelmanagementsystem.dto.ReservationDTO;
import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.service.custom.ReservationService;
import com.sachin.hostelmanagementsystem.util.FactoryConfiguration;
import org.hibernate.Session;

public class ReservationServiceImpl implements ReservationService {

    @Override
    public boolean proceedReservation(StudentDTO studentDTO, ReservationDTO reservationDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();

        return false;
    }
}
