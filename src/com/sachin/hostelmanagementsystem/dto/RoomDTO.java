package com.sachin.hostelmanagementsystem.dto;

import com.sachin.hostelmanagementsystem.entity.constants.ROOM_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDTO {

    private String room_type_id;

    private ROOM_TYPE roomType;

    private double key_money;

    private long qty;
}
