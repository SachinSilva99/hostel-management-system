package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.dto.RoomDTO;
import com.sachin.hostelmanagementsystem.dto.tms.RoomTM;
import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.service.ServiceFactory;
import com.sachin.hostelmanagementsystem.service.ServiceType;
import com.sachin.hostelmanagementsystem.service.SuperService;
import com.sachin.hostelmanagementsystem.service.custom.RoomService;
import com.sachin.hostelmanagementsystem.service.exception.InUseException;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import com.sachin.hostelmanagementsystem.util.FactoryConfiguration;
import com.sachin.hostelmanagementsystem.util.Mapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class RoomsFormController {
    private final RoomService roomService = ServiceFactory.getInstance().getService(ServiceType.ROOM);
    private final Mapper mapper = new Mapper();
    @FXML
    private TableView<RoomTM> tblRooms;

    public void initialize() {
        loadRoomsToTable();
    }

    private void loadRoomsToTable() {
        tblRooms.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        tblRooms.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("roomType"));
        tblRooms.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("key_money"));
        tblRooms.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblRooms.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("button"));
        List<RoomDTO> all = roomService.findAll();
        List<RoomTM> roomTms = all.stream().map(roomDTO -> toRoomTm(roomDTO, new Button("Del"))).collect(Collectors.toList());
        ObservableList<RoomTM> rooms = FXCollections.observableList(roomTms);
        tblRooms.setItems(rooms);
    }

    @FXML
    void tblRoomsOnClicked(MouseEvent event) {

    }

    private RoomTM toRoomTm(RoomDTO room, Button button) {
        RoomService roomService = ServiceFactory.getInstance().getService(ServiceType.ROOM);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String roomTypeId = room.getRoom_type_id();
                try {
                    RoomDTO room1 = roomService.getRoom(roomTypeId);
                    roomService.delete(room1);
                    new Alert(Alert.AlertType.INFORMATION, roomTypeId + " deleted").show();
                    loadRoomsToTable();
                } catch (NotFoundException | InUseException e) {
                    new Alert(Alert.AlertType.ERROR, roomTypeId + "in Use").show();
                }
            }
        });
        return new RoomTM(
                room.getRoom_type_id(),
                room.getRoomType(),
                room.getKey_money(),
                room.getQty(),
                button
        );
    }

}
