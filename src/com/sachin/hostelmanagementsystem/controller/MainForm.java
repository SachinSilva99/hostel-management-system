package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.util.Navigation;
import com.sachin.hostelmanagementsystem.util.Route;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainForm {
    @FXML
    public AnchorPane subAnchorPane;

    public void initialize() throws IOException {

    }

    public void test(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Route.DASHBOARD, subAnchorPane);
    }
}
