package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.util.Navigation;
import com.sachin.hostelmanagementsystem.util.Route;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainFormController {
    @FXML
    public AnchorPane subAnchorPane;
    public Label btnLoading;

    public void initialize() throws IOException {

    }


    @FXML
    public void btnRoomsOnAction(ActionEvent actionEvent) {
        Navigation.navigate(Route.ROOM, subAnchorPane);

    }

    public void btnPlaceReservationsOnAction(ActionEvent actionEvent) {
        Navigation.navigate(Route.RESERVATION, subAnchorPane);
    }

    @FXML
    public void btnDashboardOnAction(ActionEvent actionEvent) {
        Navigation.navigate(Route.DASHBOARD, subAnchorPane);
    }

    @FXML
    public void btnStudentOnAction(ActionEvent actionEvent) {
        Navigation.navigate(Route.STUDENT, subAnchorPane);
    }

    @FXML
    public void btnSettingsOnAction(ActionEvent actionEvent) {
        Navigation.navigate(Route.SETTINGS, subAnchorPane);
    }

    @FXML
    public void logoutOnClick(MouseEvent mouseEvent) throws IOException {
        Stage primaryStage = new Stage();
        URL resource = this.getClass().getResource("/com/sachin/hostelmanagementsystem/view/LoginForm.fxml");
        Parent window = FXMLLoader.load(resource);
        Scene scene = new Scene(window);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DashBoard Form");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setIconified(false);
        primaryStage.show();
        close(mouseEvent);
    }
    private void close(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
