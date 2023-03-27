package com.sachin.hostelmanagementsystem.entity;

import com.sachin.hostelmanagementsystem.entity.constants.GENDER;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Id
    private String student_id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String contact_no;
    @Column(nullable = false)
    private Date dob;
    @Column
    private GENDER gender;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<Reservation> reservations = new ArrayList<>();
}
