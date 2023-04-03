package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.dto.RoomDTO;
import com.sachin.hostelmanagementsystem.dto.tms.RoomTM;
import com.sachin.hostelmanagementsystem.entity.constants.ROOM_TYPE;
import com.sachin.hostelmanagementsystem.regex.Validates;
import com.sachin.hostelmanagementsystem.regex.Validation;
import com.sachin.hostelmanagementsystem.service.ServiceFactory;
import com.sachin.hostelmanagementsystem.service.ServiceType;
import com.sachin.hostelmanagementsystem.service.custom.RoomService;
import com.sachin.hostelmanagementsystem.service.exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomsFormController {
    private final RoomService roomService = ServiceFactory.getInstance().getService(ServiceType.ROOM);
    private  final Validation validation = new Validation();


    @FXML
    private TextField txtRoomId;

    @FXML
    private TextField txtKeyMoney;

    @FXML
    private TextField txtQty;

    @FXML
    private ComboBox<String> cmbRoomType;
    @FXML
    private TableView<RoomTM> tblRooms;

    public void initialize() {
        loadRoomsToTable();
        loadRoomTypes();
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
        RoomTM roomTM = tblRooms.getSelectionModel().getSelectedItem();
        txtRoomId.setText(roomTM.getRoom_type_id());
        txtKeyMoney.setText(String.valueOf(roomTM.getKey_money()));
        txtQty.setText(String.valueOf(roomTM.getQty()));
        ROOM_TYPE roomType = roomTM.getRoomType();
        cmbRoomType.getSelectionModel().select(getRoomTypeIndex(roomType));
    }


    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) {
        String roomId = null;

        try {
            roomId = txtRoomId.getText();
            double keyMoney = Double.parseDouble(txtKeyMoney.getText());
            int qty = Integer.parseInt(txtQty.getText());
            String roomType = cmbRoomType.getSelectionModel().getSelectedItem();

            if (roomId == null) {
                new Alert(Alert.AlertType.ERROR, "Room Id cannot be empty").show();
                return;
            }
            roomService.save(new RoomDTO(roomId, getRoomType(roomType), keyMoney, qty));
            clearFields();
            new Alert(Alert.AlertType.CONFIRMATION, "Room saved successfully").show();
            loadRoomsToTable();

        } catch (AlreadyExists e) {
            new Alert(Alert.AlertType.ERROR, roomId + " already exists").show();
        } catch (SavingFailedException e) {
            new Alert(Alert.AlertType.ERROR, roomId + " couldn't save").show();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "check the fields again").show();
        }
    }

    private void clearFields() {
        txtRoomId.clear();
        txtQty.clear();
        txtKeyMoney.clear();
        cmbRoomType.getSelectionModel().clearSelection();
    }

    @FXML
    public void btnUpdateOnActionOnAction(ActionEvent actionEvent) {
        String roomId = null;

        try {
            roomId = txtRoomId.getText();
            double keyMoney = Double.parseDouble(txtKeyMoney.getText());
            int qty = Integer.parseInt(txtQty.getText());
            String roomType = cmbRoomType.getSelectionModel().getSelectedItem();

            if (roomId == null) {
                new Alert(Alert.AlertType.ERROR, "Room Id cannot be empty").show();
                return;
            }
            roomService.update(new RoomDTO(roomId, getRoomType(roomType), keyMoney, qty));
            clearFields();
            new Alert(Alert.AlertType.CONFIRMATION, "Room updated successfully").show();
            loadRoomsToTable();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "check the fields again").show();
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, roomId + " not found").show();
        } catch (UpdateFailedException e) {
            new Alert(Alert.AlertType.ERROR, roomId + " update failed").show();
        }
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
                    clearFields();
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

    private void loadRoomTypes() {
        ArrayList<String> roomTypeList = new ArrayList<>();
        roomTypeList.add("AC");
        roomTypeList.add("AC_FOOD");
        roomTypeList.add("NON_AC_FOOD");
        roomTypeList.add("NON_AC");
        ObservableList<String> roomTypes = FXCollections.observableList(roomTypeList);
        cmbRoomType.setItems(roomTypes);
    }

    private int getRoomTypeIndex(ROOM_TYPE roomType) {
        switch (roomType) {
            case AC:
                return 0;
            case AC_FOOD:
                return 1;
            case NON_AC_FOOD:
                return 2;
            case NON_AC:
                return 3;
            default:
                return -1;
        }
    }

    private ROOM_TYPE getRoomType(String roomType) {
        switch (roomType) {
            case "AC":
                return ROOM_TYPE.AC;
            case "AC_FOOD":
                return ROOM_TYPE.AC_FOOD;
            case "NON_AC_FOOD":
                return ROOM_TYPE.NON_AC_FOOD;
            case "NON_AC":
                return ROOM_TYPE.NON_AC;
            default:
                return null;
        }
    }

    @FXML
    void txtKeyMoneyOnKeyReleased(KeyEvent event) {
        txtKeyMoney.setStyle("-fx-border-color: none;");
        boolean match = validation.match(txtKeyMoney.getText(), Validates.PRICE);
        if(match){
            txtKeyMoney.setStyle("-fx-border-color: none;");
            return;
        }
        txtKeyMoney.setStyle("-fx-border-color: #fc6161;");
    }

    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {

        txtQty.setStyle("-fx-border-color: none;");
        boolean match = validation.match(txtQty.getText(), Validates.QTY);
        if(match){
            txtQty.setStyle("-fx-border-color: none;");
            return;
        }
        txtQty.setStyle("-fx-border-color: #fc6161;");
    }

    @FXML
    void txtRoomIdOnKeyReleased(KeyEvent event) {
        System.out.println("inside");

        txtRoomId.setStyle("-fx-border-color: none;");
        boolean match = validation.match(txtRoomId.getText(), Validates.ROOM_TYPE_ID);
        if(match){
            txtRoomId.setStyle("-fx-border-color: none;");
            System.out.println("matching");
            return;
        }
        txtRoomId.setStyle("-fx-border-color: #fc6161;");
    }
}
