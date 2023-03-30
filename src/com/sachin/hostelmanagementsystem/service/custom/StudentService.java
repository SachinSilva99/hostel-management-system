package com.sachin.hostelmanagementsystem.service.custom;

import com.sachin.hostelmanagementsystem.entity.Student;
import com.sachin.hostelmanagementsystem.service.SuperService;

import java.util.List;

public interface StudentService extends SuperService {
    List<Student> findAll();
}