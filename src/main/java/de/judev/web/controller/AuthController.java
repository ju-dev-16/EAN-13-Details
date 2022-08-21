package de.judev.web.controller;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.judev.web.entity.UserEntity;
import de.judev.web.model.UserModel;
import de.judev.web.repository.UserRepository;
import de.judev.web.security.JwtTokenProvider;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

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
    public String login(@ModelAttribute("AUTH_REQUEST") UserModel userModel, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userModel.getEmail(),
                        userModel.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Cookie cookie = new Cookie("token", jwtTokenProvider.generateToken(authentication));

        cookie.setPath("/");

        response.addCookie(cookie);

        return "redirect:/";
    }
}