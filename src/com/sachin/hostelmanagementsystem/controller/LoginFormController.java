package com.sachin.hostelmanagementsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/*
Author : Sachin Silva
*/
public class LoginFormController {

    public AnchorPane anchorLogin;
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        URL resource = this.getClass().getResource("/com/sachin/hostelmanagementsystem/view/MainForm.fxml");
        Parent window = FXMLLoader.load(resource);
        Scene scene = new Scene(window);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DashBoard Form");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setIconified(false);
        primaryStage.show();
        close(event);

    }

    private void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
