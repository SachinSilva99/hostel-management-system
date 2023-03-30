package com.sachin.hostelmanagementsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class DashBoardController { @FXML
private AnchorPane subAnchorPaneDashboard;

    @FXML
    private Label lblAvailableRooms;

    @FXML
    private Label lblNoOfStudents;

    @FXML
    private ComboBox<?> cmbPendingReservations;

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
    private TextField txtSearchP;

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
    private TextField txtSearchO;

    @FXML
    private Button btnComplete;

    @FXML
    void btnAcceptOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnCompleteOnAction(ActionEvent event) {

    }

    @FXML
    void cmbOnGoingReservationsOnAction(ActionEvent event) {

    }

    @FXML
    void cmbPendingReservationsOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchPOnKeyReleased(KeyEvent event) {

    }

}
