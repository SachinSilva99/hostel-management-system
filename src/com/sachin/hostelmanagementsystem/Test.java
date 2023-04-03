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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public /* Author: Sachin */
class
Test {
    public static void main(String[] args) {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
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
//        session.save(reservation);*/
//        Student s001 = session.load(Student.class, "S001");
//        session.delete(s001);
//        transaction.commit();
       /* String currentResId = "RS009";
        String[] split = currentResId.split("RS00");
        for(String a : split){
            System.out.println(a);
        }
        int id = Integer.parseInt(split[1]);
        id += 1;
       String result = "RS00" + id;
        System.out.println(result);*/
        /*ArrayList<String> list = new ArrayList<>();
        list.add("Sachin");
        list.add("Saman");
        list.add("Sanath");
        List<String> s1 = list.stream().filter(s -> s.contains("") || s.contains("")).collect(Collectors.toList());
        System.out.println(s1);*/
        String campusId = "ABC-12123";
        String pattern = "^[A-Za-z]+-\\w+$";
        if (campusId.matches(pattern)) {
            System.out.println("Campus Id is valid");
        } else {
            System.out.println("Campus Id is invalid");
        }

    }
}
