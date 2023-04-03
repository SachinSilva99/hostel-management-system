package com.sachin.hostelmanagementsystem.dto.tms;

import com.sachin.hostelmanagementsystem.entity.constants.ROOM_TYPE;
import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomTM {

    private String room_type_id;

    private ROOM_TYPE roomType;

    private double key_money;

    private long qty;

    private Button button;
}
