package com.sachin.hostelmanagementsystem;

import com.sachin.hostelmanagementsystem.entity.Reservation;
import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.Student;
import com.sachin.hostelmanagementsystem.entity.constants.GENDER;
import com.sachin.hostelmanagementsystem.entity.constants.ROOM_TYPE;
import com.sachin.hostelmanagementsystem.entity.constants.STATUS;
import com.sachin.hostelmanagementsystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
/*        Student student = new Student(
                "S001", "sachin", "beruwala",
                "077", new Date(1999, 7, 14),
                GENDER.MALE);

        Room room = new Room("R001", ROOM_TYPE.AC, 20000, 12);

        Reservation reservation = new Reservation("reservation", new Date(), STATUS.COMPLETED, student, room);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        student.setReservations(reservations);
        room.setReservations(reservations);
        session.save(student);
        session.save(room);
        session.save(reservation);*/
        Student s001 = session.load(Student.class, "S001");
        session.delete(s001);
        transaction.commit();
    }
}
