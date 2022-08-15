package de.judev.app.service;

import org.springframework.stereotype.Service;

import de.judev.app.entity.UserEntity;
import de.judev.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository repository;

    public void saveUser(UserEntity user) {
        repository.save(user);
    }

    public int getUserLookups(Long id) {
        return repository.findUserLookupsById(id);
    }
}