package com.sachin.hostelmanagementsystem.repo.custom.impl;

import com.sachin.hostelmanagementsystem.entity.Student;
import com.sachin.hostelmanagementsystem.repo.custom.StudentRepo;
import com.sachin.hostelmanagementsystem.repo.exception.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public class StudentRepoImpl implements StudentRepo {

    @Override
    public Student save(Student student, Session session) throws ConstraintViolationException {
        try {
            String student_id = (String) session.save(student);
            return session.get(Student.class, student_id);
        } catch (Exception e) {
            throw new ConstraintViolationException("Student did not save!");
        }
    }

    @Override
    public Student update(Student student, Session session) throws ConstraintViolationException {
        try {
            session.update(student);
            return student;
        } catch (Exception e) {
            throw new ConstraintViolationException("Student did not update");
        }
    }

    @Override
    public void delete(Student student, Session session) throws ConstraintViolationException {
        try {
            session.delete(student);
        } catch (Exception e) {
            throw new ConstraintViolationException("Student did not delete");
        }
    }

    @Override
    public List<Student> findAll(Session session) {
        CriteriaQuery<Student> query = session.getCriteriaBuilder().createQuery(Student.class);
        query.from(Student.class);
        List<Student> students = session.createQuery(query).getResultList();
        session.close();
        return students;
    }

    @Override
    public Optional<Student> findByPk(String pk, Session session) {
        Student student = session.get(Student.class, pk);
        session.close();
        return student == null ? Optional.empty() : Optional.of(student);
    }

    @Override
    public boolean existByPk(String pk, Session session) {
        Student student = session.get(Student.class, pk);
        session.close();
        return student != null;
    }

    @Override
    public long count(Session session) {
        Query query = session.createQuery("select count(*) from Student");
        session.close();
        return (Long) query.uniqueResult();
    }
}