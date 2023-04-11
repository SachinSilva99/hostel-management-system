package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.dto.UserDto;
import com.sachin.hostelmanagementsystem.service.ServiceFactory;
import com.sachin.hostelmanagementsystem.service.ServiceType;
import com.sachin.hostelmanagementsystem.service.custom.UserService;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/*
Author : Sachin Silva
*/
public class LoginFormController {
    private final UserService userService = ServiceFactory.getInstance().getService(ServiceType.USER);


    public AnchorPane anchorLogin;
    @FXML
    public PasswordField psPassword;
    @FXML
    public ImageView imgEye;
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;
    private boolean eyeStatus = false;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        String username = txtUsername.getText();
        String password = txtPassword.getText().equals("") ? psPassword.getText() : txtPassword.getText();
        try {
            UserDto user = userService.getUser(1);
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                login(event);
                return;
            }
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "wrong username or password").show();

        }
        new Alert(Alert.AlertType.ERROR, "wrong username or password").show();
    }

    private void login(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        URL resource = this.getClass().getResource("/com/sachin/hostelmanagementsystem/view/MainForm.fxml");
        Parent window = FXMLLoader.load(resource);
        window.setStyle("-fx-background-color: #000000;");
        primaryStage.getIcons().add(new Image("com/sachin/hostelmanagementsystem/assets/images/hostel_logo.png"));

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

    @FXML
    public void eyeIconOnAction(MouseEvent mouseEvent) {
        if(!eyeStatus){
            txtPassword.setText(psPassword.getText());
            txtPassword.setVisible(true);
            psPassword.setVisible(false);
            eyeStatus = !eyeStatus;
            Image image = new Image("com/sachin/hostelmanagementsystem/assets/icons/eyeOpened.png");
            imgEye.setImage(image);
            txtPassword.requestFocus();
            return;
        }
        psPassword.setText(txtPassword.getText());
        txtPassword.setVisible(false);
        psPassword.setVisible(true);
        eyeStatus = !eyeStatus;
        Image image = new Image("com/sachin/hostelmanagementsystem/assets/icons/eyeClosed.png");
        imgEye.setImage(image);
        psPassword.requestFocus();
    }
}
