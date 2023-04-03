package com.sachin.hostelmanagementsystem.service.custom.impl;

import com.sachin.hostelmanagementsystem.dto.UserDto;
import com.sachin.hostelmanagementsystem.entity.User;
import com.sachin.hostelmanagementsystem.repo.RepoFactory;
import com.sachin.hostelmanagementsystem.repo.RepoType;
import com.sachin.hostelmanagementsystem.repo.custom.UserRepo;
import com.sachin.hostelmanagementsystem.service.custom.UserService;
import com.sachin.hostelmanagementsystem.service.exception.NotFoundException;
import com.sachin.hostelmanagementsystem.service.exception.UpdateFailedException;
import com.sachin.hostelmanagementsystem.util.FactoryConfiguration;
import com.sachin.hostelmanagementsystem.util.Mapper;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

/*
Author : Sachin Silva
*/
public class UserServiceImpl implements UserService {
    private final Mapper mapper = new Mapper();
    private final UserRepo userRepo = RepoFactory.getInstance().getRepo(RepoType.USER);

    @Override
    public UserDto getUser(Integer pk) throws NotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Optional<User> byPk = userRepo.findByPk(pk, session);
        if (!byPk.isPresent()) throw new NotFoundException(byPk + " not found");
        return mapper.toUserDto(byPk.get());
    }

    @Override
    public UserDto update(UserDto userDto) throws NotFoundException, UpdateFailedException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        if (!userRepo.existByPk(userDto.getId(), session)) throw new NotFoundException(userDto.getId() + " not found");
        session.clear();
        try {
            session.update(mapper.toUser(userDto));
            transaction.commit();
            return userDto;
        } catch (Exception e) {
            throw new UpdateFailedException(userDto.getId() + "Update failed");
        } finally {
            session.close();
        }
    }

    @Override
    public UserDto getByUsername(String username) throws NotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        User user = userRepo.getUserByUsername(username, session);
        if (user == null) throw new NotFoundException(username + " not found");
        return mapper.toUserDto(user);
    }

    @Override
    public long getUsersCount() {
        Session session = FactoryConfiguration.getInstance().getSession();

        return 0;
    }
}
