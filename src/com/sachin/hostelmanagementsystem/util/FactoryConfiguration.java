package com.sachin.hostelmanagementsystem.util;


import com.sachin.hostelmanagementsystem.entity.Reservation;
import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.Student;
import com.sachin.hostelmanagementsystem.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpamodelgen.xml.jaxb.Persistence;

import java.io.IOException;

public class FactoryConfiguration {
    private static final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();
    private final SessionFactory sessionFactory;

    private FactoryConfiguration()  {

        Configuration configure = new Configuration();

        configure
                .addAnnotatedClass(Reservation.class)
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(User.class);
        sessionFactory = configure.buildSessionFactory();
    }
    public static FactoryConfiguration getInstance(){
        return factoryConfiguration;
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }

}
