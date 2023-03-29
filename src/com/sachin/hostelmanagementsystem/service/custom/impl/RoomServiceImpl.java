package com.sachin.hostelmanagementsystem.service.custom.impl;

import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.constants.ROOM_TYPE;
import com.sachin.hostelmanagementsystem.repo.RepoFactory;
import com.sachin.hostelmanagementsystem.repo.RepoType;
import com.sachin.hostelmanagementsystem.repo.custom.RoomRepo;
import com.sachin.hostelmanagementsystem.service.custom.RoomService;
import com.sachin.hostelmanagementsystem.service.exception.AlreadyExists;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import com.sachin.hostelmanagementsystem.service.exception.SavingFailedException;
import com.sachin.hostelmanagementsystem.service.exception.UpdateFailedException;
import com.sachin.hostelmanagementsystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class RoomServiceImpl implements RoomService {
    private final RoomRepo roomRepo = RepoFactory.getInstance().getRepo(RepoType.ROOM);

    @Override
    public List<Room> findAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        return roomRepo.findAll(session);
    }

    @Override
    public Room save(Room room) throws AlreadyExists, SavingFailedException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (roomRepo.existByPk(room.getRoom_type_id(), session)) {
                throw new AlreadyExists(room.getRoom_type_id() + " Room Already Exists");
            }
            roomRepo.save(room, session);
            transaction.commit();
            return room;
        } catch (Exception e) {
            transaction.rollback();
            throw new SavingFailedException(room.getRoom_type_id() + " Room failed to save");
        } finally {
            session.close();
        }
    }

    @Override
    public Room update(Room room) throws NotFoundException, UpdateFailedException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        if (!roomRepo.existByPk(room.getRoom_type_id(), session)) {
            throw new NotFoundException(room.getRoom_type_id() + " room doesn't exist");
        }
        try {
            session.update(room);
            transaction.commit();
            return room;
        } catch (Exception e) {
            transaction.rollback();
            throw new UpdateFailedException(room.getRoom_type_id() + " room failed to update");
        } finally {
            session.close();
        }
    }

    @Override
    public long getAvailableRoomsCountForType(String roomType) throws NotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        ROOM_TYPE roomTypeForRepo = null;
        switch (roomType) {
            case "AC":
                roomTypeForRepo = ROOM_TYPE.AC;
                break;
            case "AC_FOOD":
                roomTypeForRepo = ROOM_TYPE.AC_FOOD;
                break;
            case "NON_AC":
                roomTypeForRepo = ROOM_TYPE.NON_AC;
                break;
            case "NON_AC_FOOD":
                roomTypeForRepo = ROOM_TYPE.NON_AC_FOOD;
            default:
                roomTypeForRepo = null;
        }
        return roomRepo.getRoomCountForType(roomTypeForRepo, session);

    }


}
