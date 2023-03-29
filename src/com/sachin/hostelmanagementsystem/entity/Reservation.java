package com.sachin.hostelmanagementsystem.entity;

import com.sachin.hostelmanagementsystem.entity.constants.STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Reservation")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reservation implements SuperEntity{
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

    public Reservation(String res_id, Date date, STATUS status) {
        this.res_id = res_id;
        this.date = date;
        this.status = status;
    }
}
