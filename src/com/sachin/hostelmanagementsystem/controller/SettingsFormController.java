package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.dto.UserDto;
import com.sachin.hostelmanagementsystem.service.ServiceFactory;
import com.sachin.hostelmanagementsystem.service.ServiceType;
import com.sachin.hostelmanagementsystem.service.custom.UserService;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import com.sachin.hostelmanagementsystem.service.exception.UpdateFailedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
Author : Sachin Silva
*/

public class SettingsFormController {
    private final UserService userService = ServiceFactory.getInstance().getService(ServiceType.USER);
    @FXML
    public Label lblUserId;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    public void initialize() throws NotFoundException {
        lblUserId.setText(String.valueOf(userService.getUser(2).getId()));
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws NotFoundException {

        int id = Integer.parseInt(lblUserId.getText());
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if(username.isEmpty() || password.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "fill the fields first").show();
            return;
        }
        UserDto userDto = userService.getUser(id);
        userDto.setUsername(username);
        userDto.setPassword(password);
        try {
            userService.update(userDto);
            new Alert(Alert.AlertType.INFORMATION, username + " updated successfully").show();
            txtPassword.clear();
            txtUsername.clear();
        } catch (UpdateFailedException e) {
            new Alert(Alert.AlertType.ERROR, username + " updated failed").show();
        }
    }
}
