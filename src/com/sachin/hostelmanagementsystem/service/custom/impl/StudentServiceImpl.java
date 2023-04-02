package com.sachin.hostelmanagementsystem.service.custom.impl;

import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.entity.Reservation;
import com.sachin.hostelmanagementsystem.entity.Student;
import com.sachin.hostelmanagementsystem.repo.RepoFactory;
import com.sachin.hostelmanagementsystem.repo.RepoType;
import com.sachin.hostelmanagementsystem.repo.custom.StudentRepo;
import com.sachin.hostelmanagementsystem.repo.exception.ConstraintViolationException;
import com.sachin.hostelmanagementsystem.service.custom.StudentService;
import com.sachin.hostelmanagementsystem.service.exception.UpdateFailedException;
import com.sachin.hostelmanagementsystem.util.FactoryConfiguration;
import com.sachin.hostelmanagementsystem.util.Mapper;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private final Mapper mapper = new Mapper();
    private final StudentRepo studentRepo = RepoFactory.getInstance().getRepo(RepoType.STUDENT);


    @Override
    public List<StudentDTO> findAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<StudentDTO> studentDTOList = studentRepo.findAll(session).stream().map(
                student -> mapper.toStudentDto(student)).collect(Collectors.toList()
        );
        session.close();
        return studentDTOList;
    }

    @Override
    public List<StudentDTO> search(String text) {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<StudentDTO> studentDTOList = studentRepo.search(text, session).stream().map(
                student -> mapper.toStudentDto(student)).collect(Collectors.toList()
        );
        session.close();
        System.out.println(studentDTOList);
        return studentDTOList;
    }

    @Override
    public List<StudentDTO> studentsWhoNoTPaidKeyMoney() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Student> students = studentRepo.studentsWhoNoTPaidKeyMoney(session);
        List<StudentDTO> studentDTOS = students.stream().map(student -> mapper.toStudentDto(student)).collect(Collectors.toList());
        session.close();
        return studentDTOS;
    }

    @Override
    public List<String> getReservationsForStudent(String studentId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Optional<Student> byPk = studentRepo.findByPk(studentId, session);
        if (!byPk.isPresent()) return new ArrayList<>();
        List<String> collect = byPk.get().getReservations().stream().map(Reservation::getRes_id).collect(Collectors.toList());
        session.close();
        return collect;
    }

    @Override
    public StudentDTO update(StudentDTO dto) throws UpdateFailedException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Student student = studentRepo.update(mapper.toStudent(dto), session);
            transaction.commit();
            return mapper.toStudentDto(student);
        } catch (ConstraintViolationException e) {
            transaction.rollback();
            throw new UpdateFailedException();
        }finally {
            session.close();
        }
    }
}
