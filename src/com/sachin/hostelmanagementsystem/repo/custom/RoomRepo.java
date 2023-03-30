package com.sachin.hostelmanagementsystem.repo.custom;

import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.constants.ROOM_TYPE;
import com.sachin.hostelmanagementsystem.repo.CrudRepo;
import org.hibernate.Session;

import java.util.List;

public interface RoomRepo extends CrudRepo<Room, String> {
    long getRoomCountForType(ROOM_TYPE roomType, Session session);

    List<String> getRoomIds(ROOM_TYPE roomType, Session session);
}

