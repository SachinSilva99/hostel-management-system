package com.sachin.hostelmanagementsystem;

import com.sachin.hostelmanagementsystem.entity.Room;
import com.sachin.hostelmanagementsystem.entity.User;
import com.sachin.hostelmanagementsystem.entity.constants.ROOM_TYPE;
import com.sachin.hostelmanagementsystem.repo.RepoFactory;
import com.sachin.hostelmanagementsystem.repo.RepoType;
import com.sachin.hostelmanagementsystem.repo.custom.RoomRepo;
import com.sachin.hostelmanagementsystem.repo.custom.UserRepo;
import com.sachin.hostelmanagementsystem.util.FactoryConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.net.URL;

public class AppInitializer extends Application {
    public static void main(String[] args) throws Exception {
        initializeDb();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL resource = this.getClass().getResource("/com/sachin/hostelmanagementsystem/view/LoginForm.fxml");
        Parent window = FXMLLoader.load(resource);
        Scene scene = new Scene(window);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DashBoard Form");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setIconified(false);
        primaryStage.show();
    }

    private static void initializeDb() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        RoomRepo roomRepo = RepoFactory.getInstance().getRepo(RepoType.ROOM);
        UserRepo userRepo = RepoFactory.getInstance().getRepo(RepoType.USER);
        if (userRepo.count(session) == 0) {
            Transaction transaction = session.beginTransaction();
            User user = new User(1,"user", "1234");
            userRepo.save(user, session);
            transaction.commit();
        }
        long count = roomRepo.count(session);
        if (count != 0) return;
        Room room1 = new Room("RM-1324", ROOM_TYPE.NON_AC, 3100.0, 35);
        Room room2 = new Room("RM-5467", ROOM_TYPE.NON_AC_FOOD, 6500.0, 20);
        Room room3 = new Room("RM-7896", ROOM_TYPE.AC, 8900.0, 14);
        Room room4 = new Room("RM-0093", ROOM_TYPE.AC_FOOD, 16000.0, 10);
        Transaction transaction = session.beginTransaction();
        try {
            roomRepo.save(room1, session);
            roomRepo.save(room2, session);
            roomRepo.save(room3, session);
            roomRepo.save(room4, session);
            transaction.commit();
        } catch (Exception e) {
            throw new Exception("Failed to initialize db");
        }
    }
}
