package com.sachin.hostelmanagementsystem.service.custom;

import com.sachin.hostelmanagementsystem.dto.ReservationDTO;
import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.entity.Student;
import com.sachin.hostelmanagementsystem.service.SuperService;

import java.util.List;

public interface StudentService extends SuperService {
    List<StudentDTO> findAll();
    List<StudentDTO> search(String text);
    List<StudentDTO> studentsWhoNoTPaidKeyMoney();

    List<String> getReservationsForStudent(String studentId);
}
