package com.sachin.hostelmanagementsystem.dto;

import com.sachin.hostelmanagementsystem.entity.constants.STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationDTO {

    private String res_id;

    private Date date;

    private STATUS status;
}
