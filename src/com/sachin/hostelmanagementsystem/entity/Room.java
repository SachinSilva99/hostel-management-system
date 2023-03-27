package com.sachin.hostelmanagementsystem.entity;

import com.sachin.hostelmanagementsystem.entity.constants.ROOM_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "room")
public class Room {
    @Id
    private String room_type_id;
    @Column(nullable = false)
    private ROOM_TYPE roomType;
    @Column(nullable = false)
    private double key_money;
    @Column(nullable = false)
    private long qty;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "room")
    private List<Reservation> reservations = new ArrayList<>();
}
