package de.judev.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.judev.app.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    
    UserEntity findByUsername(String username);

    int findUserLookupsById(Long id);
}