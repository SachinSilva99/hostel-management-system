package com.sachin.hostelmanagementsystem;

import com.sachin.hostelmanagementsystem.util.FactoryConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.net.URL;

public class AppInitializer extends Application {
    public static void main(String[] args) {
         Session session = FactoryConfiguration.getInstance().getSession();
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL resource = this.getClass().getResource("/com/sachin/hostelmanagementsystem/view/MainForm.fxml");
        Parent window = FXMLLoader.load(resource);
        Scene scene = new Scene(window);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DashBoard Form");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setIconified(false);
        primaryStage.show();
    }
}
