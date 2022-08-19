package de.judev.web.controller;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.judev.web.entity.UserEntity;
import de.judev.web.model.UserModel;
import de.judev.web.repository.UserRepository;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

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

    @PostMapping("/login")
    public String login(@ModelAttribute("AUTH_REQUEST") UserModel userModel) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userModel.getEmail(),
                        userModel.getPassword()
                )
        );

        return "redirect:/";
    }
}