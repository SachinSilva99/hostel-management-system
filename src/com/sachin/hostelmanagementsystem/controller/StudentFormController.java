package com.sachin.hostelmanagementsystem.controller;

import com.sachin.hostelmanagementsystem.dto.StudentDTO;
import com.sachin.hostelmanagementsystem.regex.Validates;
import com.sachin.hostelmanagementsystem.regex.Validation;
import com.sachin.hostelmanagementsystem.service.ServiceFactory;
import com.sachin.hostelmanagementsystem.service.ServiceType;
import com.sachin.hostelmanagementsystem.service.custom.StudentService;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import com.sachin.hostelmanagementsystem.service.exception.UpdateFailedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class StudentFormController {
    private final StudentService studentService = ServiceFactory.getInstance().getService(ServiceType.STUDENT);
    private final Validation validation = new Validation();

    @FXML
    public DatePicker dtDob;


    @FXML
    private TableView<StudentDTO> tblStudents;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtContact_no;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    private StudentDTO selectedStudentDTO;

    @FXML
    public void initialize() {
        clearFields();
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
        List<StudentDTO> studentDTOS = studentService.search(txtSearch.getText());
        ObservableList<StudentDTO> studentDTOS1 = FXCollections.observableList(studentDTOS);
        tblStudents.setItems(studentDTOS1);
        clearFields();
    }

    private void clearFields() {
        txtName.clear();
        txtAddress.clear();
        txtContact_no.clear();
        dtDob.setValue(null);
    }

    @FXML
    public void tblStudentsOnClick(MouseEvent mouseEvent) {
        TableView.TableViewSelectionModel<StudentDTO> selectionModel = tblStudents.getSelectionModel();
        if (selectionModel.getSelectedItem() == null) return;
        selectedStudentDTO = selectionModel.getSelectedItem();
        txtName.setText(selectedStudentDTO.getName());
        txtAddress.setText(selectedStudentDTO.getAddress());
        txtContact_no.setText(selectedStudentDTO.getContact_no());
        Date dob = selectedStudentDTO.getDob();
        LocalDate localDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dtDob.setValue(localDate);
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        StudentDTO dto = selectedStudentDTO;
        if(selectedStudentDTO == null){
            new Alert(Alert.AlertType.ERROR, "Select a student first! ").show();
            return;
        }
        try {
            String name = txtName.getText();
            String address = txtAddress.getText();
            String contactNo = txtContact_no.getText();
            LocalDate localDate = dtDob.getValue();
            Date dob = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dto.setName(name);
            dto.setAddress(address);
            dto.setContact_no(contactNo);
            dto.setDob(dob);
            if(!allValidated()){
                new Alert(Alert.AlertType.ERROR, "fields are not validated yet").show();
                return;
            }
            studentService.update(dto);
            new Alert(Alert.AlertType.CONFIRMATION, dto.getStudent_id() + " Successfully updated").show();
            loadStudents();
            tblStudents.refresh();
            clearFields();
        } catch (UpdateFailedException e) {
            new Alert(Alert.AlertType.ERROR, dto.getStudent_id() + " Failed to update").show();
        }catch (NotFoundException e){
            new Alert(Alert.AlertType.ERROR, dto.getStudent_id() + " not found").show();
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Check the fields again!").show();
        }
    }

    @FXML
    void dtDobOnAction(ActionEvent event) {
        dtDob.getEditor().setStyle("-fx-text-fill: #1b9a1b;");
        LocalDate selectedDate = dtDob.getValue();
        if (dtDob.getValue() == null) return;
        LocalDate today = LocalDate.now();
        int age = Period.between(selectedDate, today).getYears();

        if (age >= 18) {
            dtDob.getEditor().setStyle("-fx-text-fill: #1b9a1b;");
        } else {
            dtDob.getEditor().setStyle("-fx-text-fill: red;");
        }

    }

    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {
        txtAddress.setStyle("-fx-border-color: none;");
        boolean match = validation.match(txtAddress.getText(), Validates.ADDRESS);
        if (match) {
            txtAddress.setStyle("-fx-border-color: none;");
            return;
        }
        txtAddress.setStyle("-fx-border-color: #fc6161;");
    }

    @FXML
    void txtContact_noOnKeyReleased(KeyEvent event) {
        txtContact_no.setStyle("-fx-border-color: none;");
        boolean match = validation.match(txtContact_no.getText(), Validates.PHONE_NUMBER);
        if (match) {
            txtContact_no.setStyle("-fx-border-color: none;");
            return;
        }
        txtContact_no.setStyle("-fx-border-color: #fc6161;");
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        txtName.setStyle("-fx-border-color: none;");
        boolean match = validation.match(txtName.getText(), Validates.NAME);
        if (match) {
            txtName.setStyle("-fx-border-color: none;");
            return;
        }
        txtName.setStyle("-fx-border-color: #fc6161;");
    }

    private boolean allValidated() {
        boolean isAddressValid = validation.match(txtAddress.getText(), Validates.ADDRESS);
        boolean isContactNoValid = validation.match(txtContact_no.getText(), Validates.PHONE_NUMBER);
        boolean isNameValid = validation.match(txtName.getText(), Validates.NAME);

        if (isAddressValid && isContactNoValid && isNameValid) {
            return true;
        }
        return false;
    }
}
