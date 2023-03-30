package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.util.Navigation;
import com.sachin.hostelmanagementsystem.util.Route;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainFormController {
    @FXML
    public AnchorPane subAnchorPane;
    public Label btnLoading;

    public void initialize() throws IOException {

    }

    public void test(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.DASHBOARD, subAnchorPane);
    }

    @FXML
    public void btnRoomsOnAction(ActionEvent actionEvent) throws IOException {

    }

    public void btnPlaceReservationsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.RESERVATION, subAnchorPane);
    }

    @FXML
    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.DASHBOARD, subAnchorPane);
    }
}
