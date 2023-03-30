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
    private Label lblRoomType0;

    @FXML
    private Label lblKeyMoneyO;

    @FXML
    private Label lblDateO;

    @FXML
    private ComboBox<?> cmbOnGoingReservations;


    @FXML
    private Button btnComplete;

    public void initialize() {
        loadRemainingRooms();
        loadNoOfStudentsNotPaidKeyMoney();
        loadNotPaidStudentIds();
    }


    @FXML
    void btnAcceptOnAction(ActionEvent event) {
        try {
            reservationService.update(cmbPendingReservations.getSelectionModel().getSelectedItem(), STATUS.ACCEPTED);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Did not complete").show();
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Accepted Successfully").show();
        clearFields();

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        try {
            reservationService.update(cmbPendingReservations.getSelectionModel().getSelectedItem(), STATUS.CANCELLED);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Didi not complete").show();
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Cancelled Successfully").show();
        clearFields();
    }

    @FXML
    void btnCompleteOnAction(ActionEvent event) {


    }

    @FXML
    void cmbOnGoingReservationsOnAction(ActionEvent event) {

    }


    private void loadNotPaidStudentIds() {
        //loading the pending reservations id
        List<String> pendingReservations = reservationService.getPendingReservations();
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
        ReservationDTO dto = reservationService.getReservationDTO(res_id);

        lblReservationIdP.setText(dto.getRes_id());
        lblRoomTypeP.setText(String.valueOf(roomService.getRoom(dto.getRoomTypeId()).getRoomType()));
        lblKeyMoneyP.setText(String.valueOf(roomService.getRoom(dto.getRoomTypeId()).getKey_money()));
        lblDateP.setText(String.valueOf(dto.getDate()));

    }
    private void clearFields(){
        lblStudent_idP.setText("");
        lblRoomTypeP.setText("");
        lblKeyMoneyP.setText("");
        lblDateP.setText("");
    }
}
