package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.service.ServiceFactory;
import com.sachin.hostelmanagementsystem.service.ServiceType;
import com.sachin.hostelmanagementsystem.service.custom.ReservationService;
import com.sachin.hostelmanagementsystem.service.custom.RoomService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class ReservationsFormController {
    @FXML
    public ComboBox<String> cmbRoomId;
    @FXML
    private Label lblAvailableRoomsCount;
    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private ComboBox<String> cmbRoomType;

    @FXML
    private ComboBox<?> cmbAvailableRooms;

    @FXML
    private ComboBox<?> cmbKeyMoney;

    @FXML
    private Label lblRoomPrice;

    @FXML
    private Button btnProceed;

    private final ReservationService reservationService = ServiceFactory.getInstance().getService(ServiceType.RESERVATION);
    private final RoomService roomService = ServiceFactory.getInstance().getService(ServiceType.ROOM);

    public void initialize() {
        loadGender();
        loadRoomTypes();
        loadKeyMoneyCmb();
    }

    private void loadKeyMoneyCmb() {
        ArrayList<String> keyMoneyList = new ArrayList<>();
        keyMoneyList.add("YES");
        keyMoneyList.add("NO");
        ObservableList<String> keyMoney = FXCollections.observableList(keyMoneyList);
        cmbGender.setItems(keyMoney);
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

    private void loadGender() {
        ArrayList<String> gendersList = new ArrayList<>();
        gendersList.add("MALE");
        gendersList.add("OTHER");
        gendersList.add("FEMALE");
        ObservableList<String> genders = FXCollections.observableList(gendersList);
        cmbGender.setItems(genders);
    }

    @FXML
    public void cmbRoomTypeOnAction(ActionEvent actionEvent) {
        loadAvailableRooms();
    }

    private void loadAvailableRooms() {
        //loading how many numbers of rooms available for selected room type
        String roomType = cmbRoomType.getSelectionModel().getSelectedItem();
        long availableRoomsCountForType = roomService.getAvailableRoomsCountForType(roomType);
        lblAvailableRoomsCount.setText(String.valueOf(availableRoomsCountForType));

        //loading ids of selected room type
        List<String> roomIds = roomService.getRoomIds(roomType);
        cmbRoomId.setItems(FXCollections.observableList(roomIds));
    }

    @FXML
    public void cmbRoomIdOnAction(ActionEvent actionEvent) {
        //loading how many numbers of rooms available for selected room id
        String roomId = cmbRoomId.getSelectionModel().getSelectedItem();
        long availableRoomsCountForId = roomService.getAvailableRoomsCountForId(roomId);
        lblAvailableRoomsCount.setText(String.valueOf(availableRoomsCountForId));
    }
}
