package de.judev.web.controller;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.judev.web.entity.UserEntity;
import de.judev.web.model.UserModel;
import de.judev.web.repository.UserRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AuthController {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@ModelAttribute("AUTH_REQUEST") UserModel userModel) {

        Optional<UserEntity> userOptional = userRepository.findUserByEmail(userModel.getEmail());

        if (userOptional.isPresent()) {
            return "redirect:/";
        }

        UserEntity user = new UserEntity();
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));

        userRepository.save(user);

        return "redirect:/";
    }
}