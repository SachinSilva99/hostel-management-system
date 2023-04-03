package com.sachin.hostelmanagementsystem.service.custom;

import com.sachin.hostelmanagementsystem.dto.RoomDTO;
import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.constants.ROOM_TYPE;
import com.sachin.hostelmanagementsystem.service.SuperService;
import com.sachin.hostelmanagementsystem.service.exception.*;
import org.hibernate.Session;

import java.util.List;

public interface RoomService extends SuperService {
    long getAllAvailableRoomsCount();
    List<RoomDTO> findAll();
    RoomDTO save(RoomDTO roomDTO) throws AlreadyExists, SavingFailedException;
    RoomDTO update(RoomDTO roomDTO) throws NotFoundException, UpdateFailedException;
    long getAvailableRoomsCountForType(String roomType)throws NotFoundException;
    long getAvailableRoomsCountForId(String roomId)throws NotFoundException;
    List<String> getRoomIds(String roomType);
    RoomDTO getRoom(String roomId)throws NotFoundException;

    void delete(RoomDTO selectedRoom) throws InUseException;
}
