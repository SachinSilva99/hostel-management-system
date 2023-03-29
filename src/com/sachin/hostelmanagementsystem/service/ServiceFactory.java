package com.sachin.hostelmanagementsystem.service;

import com.sachin.hostelmanagementsystem.service.custom.impl.ReservationServiceImpl;
import com.sachin.hostelmanagementsystem.service.custom.impl.RoomServiceImpl;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return (serviceFactory == null) ? new ServiceFactory() : serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceType serviceType) {
        switch (serviceType) {
            case RESERVATION:
                return (T) new ReservationServiceImpl();
            case ROOM:
                return (T) new RoomServiceImpl();
            default:
                return null;
        }
    }

}
