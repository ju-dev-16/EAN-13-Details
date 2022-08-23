package de.judev.web.controller;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.judev.web.entity.UserEntity;
import de.judev.web.model.InputModel;
import de.judev.web.service.BarcodeDetailsService;
import de.judev.web.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/details")
@RequiredArgsConstructor
public class DetailsController {
    
    private final BarcodeDetailsService detailsService;

    private final UserServiceImpl userService;

    @PostMapping
    public String showDetails(Model model, @ModelAttribute("USER_INPUT") InputModel inputModel) throws IOException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;

        if (principal instanceof UserDetails) {

            email = ((UserDetails) principal).getUsername();

        } else {

            email = principal.toString();

        }

        UserEntity user = userService.findUserByEmail(email);

        if (user.getLookups() == 0) {
            return "redirect:/";
        }

        model.addAttribute("DETAILS", detailsService.getBarcodeDetails(inputModel.getBarcode()));

        user.setLookups(user.getLookups() - 1);

        userService.updateUser(user);

        return "details";
    }
}