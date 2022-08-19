package de.judev.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.judev.web.entity.UserEntity;
import de.judev.web.repository.UserRepository;

@SpringBootApplication
public class WebApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Override
    public void run(String... args) {

		UserEntity user = new UserEntity();

		user.setEmail("test@gmail.com");
		user.setPassword("test123");

		userRepository.save(user);
    }

}