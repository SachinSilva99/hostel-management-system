package com.sachin.hostelmanagementsystem.service.custom.impl;

import com.sachin.hostelmanagementsystem.entity.Student;
import com.sachin.hostelmanagementsystem.repo.RepoFactory;
import com.sachin.hostelmanagementsystem.repo.RepoType;
import com.sachin.hostelmanagementsystem.repo.custom.StudentRepo;
import com.sachin.hostelmanagementsystem.service.custom.StudentService;
import com.sachin.hostelmanagementsystem.util.FactoryConfiguration;
import org.hibernate.Session;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    @Override
    public List<Student> findAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        StudentRepo repo = RepoFactory.getInstance().getRepo(RepoType.STUDENT);
        return repo.findAll(session);
    }
}
