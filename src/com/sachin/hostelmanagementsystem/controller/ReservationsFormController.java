package com.sachin.hostelmanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ReservationsFormController {
    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private ComboBox<?> cmbGender;

    @FXML
    private ComboBox<?> cmbRoomType;

    @FXML
    private ComboBox<?> cmbAvailableRooms;

    @FXML
    private ComboBox<?> cmbKeyMoney;

    @FXML
    private Label lblRoomPrice;

    @FXML
    private Button btnProceed;
}
