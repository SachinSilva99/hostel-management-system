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
        clearFields();

        TableView.TableViewSelectionModel<StudentDTO> selectionModel = tblStudents.getSelectionModel();

        if (selectionModel.getSelectedItem() == null) return;
        StudentDTO selectedItem = selectionModel.getSelectedItem();
        txtName.setText(selectedItem.getName());
        txtAddress.setText(selectedItem.getAddress());
        txtContact_no.setText(selectedItem.getContact_no());
        Date dob = selectedItem.getDob();
        LocalDate localDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dtDob.setValue(localDate);
        validateAll();
        selectedStudentDTO = selectionModel.getSelectedItem();

    }

    @FXML
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (selectedStudentDTO == null) {
            new Alert(Alert.AlertType.ERROR, "Select a student first! ").show();
            return;
        }
        StudentDTO dto = new StudentDTO();
        dto.setStudent_id(selectedStudentDTO.getStudent_id());
        dto.setName(selectedStudentDTO.getName());
        dto.setAddress(selectedStudentDTO.getAddress());
        dto.setContact_no(selectedStudentDTO.getContact_no());
        dto.setDob(selectedStudentDTO.getDob());

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
            dto.setGender(selectedStudentDTO.getGender());
            validateAll();
            if (!allValidated()) {
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
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, dto.getStudent_id() + " not found").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Check the fields again!").show();
        }
    }

    @FXML
    void dtDobOnAction(ActionEvent event) {
        validateDob();

    }

    private void validateDob() {
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
        setValidates(txtAddress, Validates.ADDRESS);
    }

    @FXML
    void txtContact_noOnKeyReleased(KeyEvent event) {
        setValidates(txtContact_no, Validates.PHONE_NUMBER);
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        setValidates(txtName,Validates.NAME);
    }

    private boolean allValidated() {
        boolean isAddressValid = validation.match(txtAddress.getText(), Validates.ADDRESS);
        boolean isContactNoValid = validation.match(txtContact_no.getText(), Validates.PHONE_NUMBER);
        boolean isNameValid = validation.match(txtName.getText(), Validates.NAME);
        boolean isAgeValid = isAgeValid();
        return isAddressValid && isContactNoValid && isNameValid && isAgeValid;
    }

    private boolean isAgeValid() {
        LocalDate selectedDate = dtDob.getValue();
        if (dtDob.getValue() == null) return false;
        LocalDate today = LocalDate.now();
        int age = Period.between(selectedDate, today).getYears();
        return age >= 18;
    }

    private void validateAll() {
        setValidates(txtAddress, Validates.ADDRESS);
        setValidates(txtContact_no, Validates.PHONE_NUMBER);
        validateDob();
        setValidates(txtName,Validates.NAME);
    }

    private void setValidates(TextField textField, Validates validates) {
        textField.setStyle("-fx-border-color: none;");
        boolean match = validation.match(textField.getText(), validates);
        if (match) {
            textField.setStyle("-fx-border-color: none;");
            return;
        }
        textField.setStyle("-fx-border-color: #fc6161;");
    }
}
