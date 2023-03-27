package com.sachin.hostelmanagementsystem.util;


import com.sachin.hostelmanagementsystem.entity.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configure = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Test.class);
        sessionFactory = configure.buildSessionFactory();
    }
    public static FactoryConfiguration getInstance(){
        return factoryConfiguration;
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }

}
