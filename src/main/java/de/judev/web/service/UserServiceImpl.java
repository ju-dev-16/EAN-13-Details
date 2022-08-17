package de.judev.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.judev.web.entity.UserEntity;
import de.judev.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<UserEntity> findAllUsers() {
        return repository.findAllUsers();
    }

    @Override
    public void updateUserLookupsById(Long id) {

        Optional<UserEntity> user = repository.findById(id);

        user.get().setLookups(5);

        repository.save(user.get());
    }
}