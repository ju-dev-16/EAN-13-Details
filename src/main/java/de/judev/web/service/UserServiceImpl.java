package de.judev.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.judev.web.entity.UserEntity;
import de.judev.web.model.UserModel;
import de.judev.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UserEntity findUserByEmail(String email) {
        return repository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Dieser Benutzer wurde nicht gefunden"));
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public void saveUser(UserModel userModel) {

        UserEntity user = new UserEntity();

        user.setEmail(userModel.getEmail());
        user.setPassword(userModel.getPassword());

        repository.save(user);
    }

    @Override
    public void updateUser(UserEntity user) {
        repository.save(user);        
    }

    @Override
    public void updateUserLookupsById(Long id) {

        Optional<UserEntity> user = repository.findById(id);

        user.get().setLookups(5);

        repository.save(user.get());
    }
}