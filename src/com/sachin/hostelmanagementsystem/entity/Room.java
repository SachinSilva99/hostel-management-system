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
public class Room implements SuperEntity{
    @Id
    private String room_type_id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ROOM_TYPE roomType;
    @Column(nullable = false)
    private double key_money;
    @Column(nullable = false)
    private long qty;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "room")
    private List<Reservation> reservations = new ArrayList<>();

    public Room(String room_type_id, ROOM_TYPE roomType, double key_money, long qty) {
        this.room_type_id = room_type_id;
        this.roomType = roomType;
        this.key_money = key_money;
        this.qty = qty;
    }
}
