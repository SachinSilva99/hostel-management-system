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


    @FXML
    public void btnRoomsOnAction(ActionEvent actionEvent)  {
        Navigation.navigate(Route.ROOM, subAnchorPane);

    }

    public void btnPlaceReservationsOnAction(ActionEvent actionEvent)  {
        Navigation.navigate(Route.RESERVATION, subAnchorPane);
    }

    @FXML
    public void btnDashboardOnAction(ActionEvent actionEvent)  {
        Navigation.navigate(Route.DASHBOARD, subAnchorPane);
    }

    @FXML
    public void btnStudentOnAction(ActionEvent actionEvent) {
        Navigation.navigate(Route.STUDENT, subAnchorPane);
    }
}
