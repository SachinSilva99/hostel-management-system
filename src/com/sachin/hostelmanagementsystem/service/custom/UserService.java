package com.sachin.hostelmanagementsystem.service.custom;

import com.sachin.hostelmanagementsystem.dto.UserDto;
import com.sachin.hostelmanagementsystem.service.SuperService;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import com.sachin.hostelmanagementsystem.service.exception.UpdateFailedException;

/*
Author : Sachin Silva
*/
public interface UserService extends SuperService {
    UserDto getUser(Integer pk) throws NotFoundException;
    UserDto update(UserDto userDto)throws NotFoundException, UpdateFailedException;
    UserDto getByUsername(String username)throws NotFoundException;
    long getUsersCount();
}
