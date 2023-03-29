package com.sachin.hostelmanagementsystem.service.custom;

import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.constants.ROOM_TYPE;
import com.sachin.hostelmanagementsystem.service.SuperService;
import com.sachin.hostelmanagementsystem.service.exception.AlreadyExists;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import com.sachin.hostelmanagementsystem.service.exception.UpdateFailedException;

import java.util.List;

public interface RoomService extends SuperService {
    List<Room> findAll();
    Room save(Room room) throws AlreadyExists;
    Room update(Room room) throws NotFoundException, UpdateFailedException;
    long getAvailableRoomsCountForType(String roomType)throws NotFoundException;

}
