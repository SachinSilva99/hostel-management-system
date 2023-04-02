package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.service.ServiceFactory;
import com.sachin.hostelmanagementsystem.service.ServiceType;
import com.sachin.hostelmanagementsystem.service.custom.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentFormController {
    private final StudentService  studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENT);


    @FXML
    private TableView<StudentDTO> tblStudents;

    @FXML
    private TableColumn<?, ?> col_student_id;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colContactNo;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtContact_no;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private Button btnUpdate;

    @FXML
    public void initialize(){
        loadStudents();
    }

    private void loadStudents() {
        tblStudents.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("student_id"));
        tblStudents.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudents.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudents.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblStudents.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("gender"));
        tblStudents.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        List<StudentDTO> all = studentService.findAll();
        ObservableList<StudentDTO> studentDTOS = FXCollections.observableList(all);
        tblStudents.setItems(studentDTOS);
    }

    @FXML
    void txtSearchOnKeyReleased(KeyEvent event) {

    }
}
