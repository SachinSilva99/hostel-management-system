package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.dto.ReservationDTO;
import com.sachin.hostelmanagementsystem.dto.RoomDTO;
import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.entity.constants.GENDER;
import com.sachin.hostelmanagementsystem.entity.constants.STATUS;
import com.sachin.hostelmanagementsystem.service.ServiceFactory;
import com.sachin.hostelmanagementsystem.service.ServiceType;
import com.sachin.hostelmanagementsystem.service.custom.ReservationService;
import com.sachin.hostelmanagementsystem.service.custom.RoomService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Date;
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
    private ComboBox<String> cmbKeyMoney;

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
        cmbKeyMoney.setItems(keyMoney);
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
        long availableRoomsCountForType = 0;

        if (roomType != null) {
            availableRoomsCountForType = roomService.getAvailableRoomsCountForType(roomType);
            lblAvailableRoomsCount.setText(String.valueOf(availableRoomsCountForType));
        }

        //loading ids of selected room type
        if (availableRoomsCountForType != 0) {
            List<String> roomIds = roomService.getRoomIds(roomType);
            cmbRoomId.setItems(FXCollections.observableList(roomIds));
        }
    }

    @FXML
    public void cmbRoomIdOnAction(ActionEvent actionEvent) {
        //loading how many numbers of rooms available for selected room id
        String roomId = cmbRoomId.getSelectionModel().getSelectedItem();
        long availableRoomsCountForId = 0;
        if (roomId != null) {
            availableRoomsCountForId = roomService.getAvailableRoomsCountForId(roomId);
            lblAvailableRoomsCount.setText(String.valueOf(availableRoomsCountForId));
        }

        //Loading key money to selected room id
        if (availableRoomsCountForId != 0) {
            RoomDTO roomDTO = roomService.getRoom(roomId);
            double keyMoney = roomDTO.getKey_money();
            lblRoomPrice.setText(String.valueOf(keyMoney));
        }
    }

    public void btnProceedOnAction(ActionEvent actionEvent) {
        String studentName = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String studentId = txtStudentId.getText();
        String selectedItem = cmbGender.getSelectionModel().getSelectedItem();
        String roomId = cmbRoomId.getSelectionModel().getSelectedItem();
        STATUS status = cmbKeyMoney.getSelectionModel().getSelectedItem().equals("YES") ? STATUS.ACCEPTED : STATUS.PENDING;


        StudentDTO studentDTO = new StudentDTO(
                studentId, studentName, address, contact, new Date(), getGender(selectedItem)
        );
        ReservationDTO reservationDTO = new ReservationDTO(
                "Res005", new Date(), status, roomId,studentDTO
        );
        try {
            reservationService.proceedReservation(reservationDTO);
            clearAllFields();
            new Alert(Alert.AlertType.CONFIRMATION, "Reservation Successful").show();
        }catch (Exception e){
            System.out.println( e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Reservation Failed").show();
        }
    }

    private void clearAllFields() {
        txtStudentId.clear();
        txtName.clear();
        txtContact.clear();
        txtAddress.clear();
        cmbGender.getSelectionModel().clearSelection();
        cmbRoomId.getSelectionModel().clearSelection();
        cmbKeyMoney.getSelectionModel().clearSelection();
        cmbRoomType.getSelectionModel().getSelectedItem();
        lblRoomPrice.setText("");
        lblAvailableRoomsCount.setText("");
    }

    private static GENDER getGender(String selectedItem) {
        switch (selectedItem) {
            case "MALE":
                return GENDER.MALE;
            case "FEMALE":
                return GENDER.FEMALE;
            case "OTHER":
                return GENDER.OTHER;
            default:
                return null;
        }
    }
}
