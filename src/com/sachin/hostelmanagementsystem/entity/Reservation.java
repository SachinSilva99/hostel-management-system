package com.sachin.hostelmanagementsystem.entity;

import com.sachin.hostelmanagementsystem.entity.constants.STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reservation {
    @Id
    private String res_id;
    @Column
    private Date date;
    @Column
    private STATUS status;
    @ManyToOne (fetch = FetchType.LAZY)
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;
}