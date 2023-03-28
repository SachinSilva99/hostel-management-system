package com.sachin.hostelmanagementsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class DashBoardController {
    @FXML
    public Label lblDate;
    @FXML
    private AnchorPane subAnchorPaneDashboard;

    @FXML
    private Label lblAvailableRooms;

    @FXML
    private Label lblNoOfStudents;

    @FXML
    private ComboBox<?> cmbPendingReservations;

    @FXML
    private Button btnComplete;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblReservationId;

    @FXML
    private Label lblRoomType;

    @FXML
    private Label lblKeyMoney;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnCompleteOnAction(ActionEvent event) {

    }

    @FXML
    void cmbPendingReservationsOnAction(ActionEvent event) {

    }
}
