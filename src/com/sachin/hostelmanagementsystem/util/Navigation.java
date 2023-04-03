package com.sachin.hostelmanagementsystem.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;
    public static void navigate(Route route, AnchorPane pane)  {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        System.out.println(pane);
        Stage window = (Stage) Navigation.pane.getScene().getWindow();

        switch (route) {
            case DASHBOARD:
                window.setTitle("DashBoard Form");
                initUI("DashBoard.fxml");
                break;
            case RESERVATION:
                window.setTitle("Reservation");
                initUI("ReservationsForm.fxml");
                break;
            case STUDENT:
                window.setTitle("Student");
                initUI("StudentForm.fxml");
                break;
            case ROOM:
                window.setTitle("Room");
                initUI("RoomsForm.fxml");
                break;
            case SETTINGS:
                window.setTitle("Settings");
                initUI("SettingsForm.fxml");
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
        }
    }
    private static void initUI(String location)  {
        try {
            Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class
                    .getResource("/com/sachin/hostelmanagementsystem/view/" + location)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
