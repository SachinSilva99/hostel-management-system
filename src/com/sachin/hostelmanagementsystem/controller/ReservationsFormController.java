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
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationsFormController {
    @FXML
    public ComboBox<String> cmbRoomId;
    @FXML
    public DatePicker dtDob;
    public Label lblResId;
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


    private final ReservationService reservationService = ServiceFactory.getInstance().getService(ServiceType.RESERVATION);
    private final RoomService roomService = ServiceFactory.getInstance().getService(ServiceType.ROOM);

    public void initialize() {
        loadGender();
        loadRoomTypes();
        loadKeyMoneyCmb();
        loadLastRes_id(null);
    }

    private void loadLastRes_id(String res_id) {
        String resId = reservationService.generateResId(res_id);
        lblResId.setText(resId);
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
            try {
                availableRoomsCountForType = roomService.getAvailableRoomsCountForType(roomType);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
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
            try {
                availableRoomsCountForId = roomService.getAvailableRoomsCountForId(roomId);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
            lblAvailableRoomsCount.setText(String.valueOf(availableRoomsCountForId));
        }

        //Loading key money to selected room id
        if (availableRoomsCountForId != 0) {
            RoomDTO roomDTO;
            try {
                roomDTO = roomService.getRoom(roomId);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
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
        STATUS status = cmbKeyMoney
                .getSelectionModel().getSelectedItem()
                .equals("YES") ? STATUS.ACCEPTED : STATUS.PENDING;
        LocalDate localDate = dtDob.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        StudentDTO studentDTO = new StudentDTO(
                studentId, studentName, address, contact, date, getGender(selectedItem)
        );
        ReservationDTO reservationDTO = new ReservationDTO(
                reservationService.generateResId(lblResId.getText()), new Date(), status, roomId,studentDTO
        );
        try {
            reservationService.proceedReservation(reservationDTO);
            clearAllFields();
            loadLastRes_id(lblResId.getText());
            new Alert(Alert.AlertType.CONFIRMATION, "Reservation Successful").show();
        }catch (Exception e){
            System.out.println( e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Reservation Failed").show();
        }
    }

    private void clearAllFields() {
        dtDob.setValue(null);
        txtStudentId.clear();
        txtName.clear();
        txtContact.clear();
        txtAddress.clear();
        cmbGender.getSelectionModel().clearSelection();
        cmbRoomId.getSelectionModel().clearSelection();
        cmbKeyMoney.getSelectionModel().clearSelection();
        cmbRoomType.getSelectionModel().clearSelection();
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
