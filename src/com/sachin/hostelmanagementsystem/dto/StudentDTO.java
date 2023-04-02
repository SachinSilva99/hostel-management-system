package com.sachin.hostelmanagementsystem.dto;

import com.sachin.hostelmanagementsystem.entity.constants.GENDER;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO implements SuperDTO{
    private String student_id;
    private String name;

    private String address;

    private String contact_no;

    private Date dob;

    private GENDER gender;

}
