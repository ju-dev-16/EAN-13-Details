package de.judev.app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.judev.app.entity.UserEntity;
import de.judev.app.repository.UserRepository;
import de.judev.app.security.AppUserPrincipal;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {

            throw new UsernameNotFoundException(username);

        }

        return new AppUserPrincipal(user);
    }
}