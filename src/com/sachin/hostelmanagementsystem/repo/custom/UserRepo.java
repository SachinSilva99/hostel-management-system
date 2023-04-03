package com.sachin.hostelmanagementsystem.repo.custom;

import com.sachin.hostelmanagementsystem.entity.User;
import com.sachin.hostelmanagementsystem.repo.CrudRepo;
import com.sachin.hostelmanagementsystem.repo.exception.ConstraintViolationException;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

/*
Author : Sachin Silva
*/
public interface UserRepo extends CrudRepo<User,Integer> {
    User getUserByUsername(String username, Session session) throws NotFoundException;
}
