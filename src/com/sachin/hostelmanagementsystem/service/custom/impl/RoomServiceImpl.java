package com.sachin.hostelmanagementsystem.service.custom.impl;

import com.sachin.hostelmanagementsystem.dto.RoomDTO;
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
import com.sachin.hostelmanagementsystem.util.Mapper;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomServiceImpl implements RoomService {
    private final RoomRepo roomRepo = RepoFactory.getInstance().getRepo(RepoType.ROOM);
    private final Mapper mapper = new Mapper();

    @Override
    public List<RoomDTO> findAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        return roomRepo.findAll(session).stream().map(mapper::toRoomDto).collect(Collectors.toList());
    }

    @Override
    public RoomDTO save(RoomDTO roomDto) throws AlreadyExists, SavingFailedException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (roomRepo.existByPk(roomDto.getRoom_type_id(), session)) {
                throw new AlreadyExists(roomDto.getRoom_type_id() + " Room Already Exists");
            }
            roomRepo.save(mapper.toRoom(roomDto), session);
            transaction.commit();
            return roomDto;
        } catch (Exception e) {
            transaction.rollback();
            throw new SavingFailedException(roomDto.getRoom_type_id() + " Room failed to save");
        } finally {
            session.close();
        }
    }

    @Override
    public RoomDTO update(RoomDTO roomDTO) throws NotFoundException, UpdateFailedException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        if (!roomRepo.existByPk(roomDTO.getRoom_type_id(), session)) {
            throw new NotFoundException(roomDTO.getRoom_type_id() + " room doesn't exist");
        }
        try {
            roomRepo.update(mapper.toRoom(roomDTO), session);
            transaction.commit();
            return roomDTO;
        } catch (Exception e) {
            transaction.rollback();
            throw new UpdateFailedException(roomDTO.getRoom_type_id() + " room failed to update");
        } finally {
            session.close();
        }
    }

    @Override
    public long getAvailableRoomsCountForType(String roomType) throws NotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        ROOM_TYPE roomTypeForRepo = getRoomType(roomType);
        long roomCountForType = roomRepo.getRoomCountForType(roomTypeForRepo, session);
        session.close();
        return roomCountForType;
    }

    @Override
    public long getAvailableRoomsCountForId(String roomId) throws NotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Optional<Room> byPk = roomRepo.findByPk(roomId, session);
        if (!byPk.isPresent()) return 0;
        session.close();
        return byPk.get().getQty();
    }

    private static ROOM_TYPE getRoomType(String roomType) {
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
                break;
            default:
                roomTypeForRepo = null;
        }
        return roomTypeForRepo;
    }

    @Override
    public List<String> getRoomIds(String roomType) {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<String> roomIds = roomRepo.getRoomIds(getRoomType(roomType), session);
        session.close();
        return roomIds;
    }

    @Override
    public RoomDTO getRoom(String roomId) throws NotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Optional<Room> byPk = roomRepo.findByPk(roomId, session);
        if (!byPk.isPresent()) {
            session.close();
            throw new NotFoundException(roomId + " Room not found");
        }
        return mapper.toRoomDto(byPk.get());
    }
}
