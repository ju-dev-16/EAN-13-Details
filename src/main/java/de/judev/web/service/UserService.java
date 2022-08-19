package de.judev.web.service;

import java.util.List;

import de.judev.web.entity.UserEntity;
import de.judev.web.model.UserModel;

public interface UserService {
    
    UserEntity findUserByEmail(String email);

    List<UserEntity> findAllUsers();

    void saveUser(UserModel user);

    void updateUser(UserEntity user);

    void updateUserLookupsById(Long id);

}