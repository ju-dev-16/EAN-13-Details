package de.judev.web.service;

import java.util.List;

import de.judev.web.entity.UserEntity;

public interface UserService {
    
    List<UserEntity> findAllUsers();

    void updateUserLookupsById(Long id);

}