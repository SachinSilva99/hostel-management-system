package com.sachin.hostelmanagementsystem.repo.custom;

import com.sachin.hostelmanagementsystem.entity.Student;
import com.sachin.hostelmanagementsystem.repo.CrudRepo;
import org.hibernate.Session;

import java.util.List;

public interface StudentRepo extends CrudRepo<Student, String> {
    List<Student> studentsWhoNoTPaidKeyMoney(Session session);
}
