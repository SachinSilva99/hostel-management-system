package com.sachin.hostelmanagementsystem.repo;

import com.sachin.hostelmanagementsystem.repo.custom.impl.ReservationRepoImpl;
import com.sachin.hostelmanagementsystem.repo.custom.impl.RoomRepoImpl;
import com.sachin.hostelmanagementsystem.repo.custom.impl.StudentRepoImpl;
import com.sachin.hostelmanagementsystem.repo.custom.impl.UserRepoImpl;

public class RepoFactory {
    private static RepoFactory repoFactory;
    private RepoFactory(){}
    public static RepoFactory getInstance(){
        return (repoFactory == null) ? new RepoFactory() : repoFactory;
    }
    public <T extends SuperRepo>T getRepo(RepoType repoType){
        switch (repoType){
            case ROOM:
                return (T) new RoomRepoImpl();
            case STUDENT:
                return (T) new StudentRepoImpl();
            case RESERVATION:
                return (T) new ReservationRepoImpl();
            case USER:
                return (T) new UserRepoImpl();
            default:
                return null;
        }
    }
}
