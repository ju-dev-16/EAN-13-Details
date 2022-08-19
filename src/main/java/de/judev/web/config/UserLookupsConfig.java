package de.judev.web.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import de.judev.web.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class UserLookupsConfig {
    
    private final UserServiceImpl userService;

    @Scheduled(fixedRate = 3600000L)
    public void handleLookups() {

        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");  
        String strDate = formatter.format(date);  

        if (strDate.equals("00:00:00")) {
            userService.findAllUsers().stream().forEach(user -> {
                userService.updateUserLookupsById(user.getId());
            });
        }
    }
}
