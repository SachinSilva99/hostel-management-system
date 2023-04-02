package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.dto.ReservationDTO;
import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.entity.constants.STATUS;
import com.sachin.hostelmanagementsystem.service.ServiceFactory;
import com.sachin.hostelmanagementsystem.service.ServiceType;
import com.sachin.hostelmanagementsystem.service.SuperService;
import com.sachin.hostelmanagementsystem.service.custom.ReservationService;
import com.sachin.hostelmanagementsystem.service.custom.RoomService;
import com.sachin.hostelmanagementsystem.service.custom.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.util.List;
import java.util.stream.Collectors;

public class DashBoardController {
    private final RoomService roomService = ServiceFactory.getInstance().getService(ServiceType.ROOM);
    private final StudentService studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENT);
    private final ReservationService reservationService = ServiceFactory.getInstance().getService(ServiceType.RESERVATION);
    @FXML
    public Label lblStudent_idP;
    @FXML
    public Label lblStudentIdO;

    @FXML
    private AnchorPane subAnchorPaneDashboard;

    @FXML
    private Label lblAvailableRooms;

    @FXML
    private Label lblNoOfStudents;

    @FXML
    private ComboBox<String> cmbPendingReservations;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblReservationIdP;

    @FXML
    private Label lblRoomTypeP;

    @FXML
    private Label lblKeyMoneyP;

    @FXML
    private Label lblDateP;

    @FXML
    private Label lblReservationIdO;

    @FXML
    private Label lblRoomTypeO;

    @FXML
    private Label lblKeyMoneyO;

    @FXML
    private Label lblDateO;

    @FXML
    private ComboBox<String> cmbOnGoingReservations;


    @FXML
    private Button btnComplete;

    public void initialize() {
        loadRemainingRooms();
        loadNoOfStudentsNotPaidKeyMoney();
        loadNotPaidStudentIds();
        loadOnGoingReservations();
    }


    @FXML
    void btnAcceptOnAction(ActionEvent event) {
        String res_id = cmbPendingReservations.getSelectionModel().getSelectedItem();
        if (res_id == null) {
            new Alert(Alert.AlertType.ERROR, "Select a reservation first").show();
            return;
        }
        try {
            reservationService.update(res_id, STATUS.ACCEPTED);
            new Alert(Alert.AlertType.CONFIRMATION, "Accepted Successfully").show();
            clearFields();
            cmbPendingReservations.getSelectionModel().clearSelection();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Did not accept").show();
        }


    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        String res_id = cmbPendingReservations.getSelectionModel().getSelectedItem();
        if (res_id == null) {
            new Alert(Alert.AlertType.ERROR, "Select a reservation first").show();
            return;
        }
        try {
            reservationService.update(res_id, STATUS.CANCELLED);
            new Alert(Alert.AlertType.CONFIRMATION, "Cancelled Successfully").show();
            clearFields();
            cmbPendingReservations.getSelectionModel().clearSelection();
            loadNotPaidStudentIds();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Did not cancel").show();
        }
    }

    @FXML
    void btnCompleteOnAction(ActionEvent event) {
        String res_id = cmbOnGoingReservations.getSelectionModel().getSelectedItem();
        if (res_id != null) {
            try {
                reservationService.update(res_id, STATUS.COMPLETED);
                new Alert(Alert.AlertType.CONFIRMATION, res_id + " Completed Successfully").show();
                clearFieldsC();
                cmbPendingReservations.getSelectionModel().clearSelection();
                loadNotPaidStudentIds();
                loadOnGoingReservations();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, res_id + " did not complete").show();
            }
            return;
        }
        new Alert(Alert.AlertType.ERROR, "Select a Reservation First").show();
    }

    @FXML
    void cmbOnGoingReservationsOnAction(ActionEvent event) {
        String resId = cmbOnGoingReservations.getSelectionModel().getSelectedItem();
        if (resId == null) return;
        ReservationDTO dto = reservationService.getReservation(resId);
        lblReservationIdO.setText(dto.getRes_id());
        lblRoomTypeO.setText(String.valueOf(roomService.getRoom(dto.getRoomTypeId()).getRoomType()));
        lblKeyMoneyO.setText(String.valueOf(roomService.getRoom(dto.getRoomTypeId()).getKey_money()));
        lblDateO.setText(String.valueOf(dto.getDate()));
        lblStudentIdO.setText(dto.getStudentDTO().getStudent_id());


    }

    private void loadOnGoingReservations() {
        List<String> reservations = reservationService.getReservations(STATUS.ACCEPTED);
        cmbOnGoingReservations.setItems(FXCollections.observableList(reservations));
    }


    private void loadNotPaidStudentIds() {
        //loading the pending reservations id
        List<String> pendingReservations = reservationService.getReservations(STATUS.PENDING);
        System.out.println(pendingReservations);
        ObservableList<String> strings = FXCollections.observableList(pendingReservations);
        cmbPendingReservations.setItems(strings);
    }

    private void loadRemainingRooms() {
        String allAvailableRoomsCount = String.valueOf(roomService.getAllAvailableRoomsCount());
        lblAvailableRooms.setText(allAvailableRoomsCount);
    }

    private void loadNoOfStudentsNotPaidKeyMoney() {
        List<StudentDTO> studentDTOS = studentService.studentsWhoNoTPaidKeyMoney();
        studentDTOS.forEach(System.out::println);
        lblNoOfStudents.setText(String.valueOf(studentDTOS.size()));
    }

    @FXML
    public void cmbPendingReservationsOnAction(ActionEvent actionEvent) {
        String res_id = cmbPendingReservations.getSelectionModel().getSelectedItem();
        if (res_id == null) return;
        ReservationDTO dto = reservationService.getReservationDTO(res_id);
        lblReservationIdP.setText(dto.getRes_id());
        lblRoomTypeP.setText(String.valueOf(roomService.getRoom(dto.getRoomTypeId()).getRoomType()));
        lblKeyMoneyP.setText(String.valueOf(roomService.getRoom(dto.getRoomTypeId()).getKey_money()));
        lblDateP.setText(String.valueOf(dto.getDate()));
        lblStudent_idP.setText(dto.getStudentDTO().getStudent_id());

    }

    private void clearFields() {
        lblStudent_idP.setText("");
        lblRoomTypeP.setText("");
        lblKeyMoneyP.setText("");
        lblDateP.setText("");
    }

    private void clearFieldsC() {
        lblReservationIdO.setText("");
        lblRoomTypeO.setText("");
        lblKeyMoneyO.setText("");
        lblDateO.setText("");
        lblStudentIdO.setText("");
    }
}
