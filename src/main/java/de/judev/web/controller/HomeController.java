package de.judev.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.judev.web.model.InputModel;
import de.judev.web.model.UserModel;
import de.judev.web.service.BarcodeValidatorService;
import de.judev.web.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final BarcodeValidatorService validatorService;
    private final UserServiceImpl userService;

    private UserModel userModel = new UserModel();
    private InputModel inputModel = new InputModel();
    
    private boolean isValid = false;
    
    @GetMapping
    public String showHome(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getName().equals("anonymousUser")) {

            String username;

            if (authentication.getPrincipal() instanceof UserDetails) {
    
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
    
            } else {
    
                username = authentication.getPrincipal().toString();
    
            }
    
            model.addAttribute("LOOKUPS", userService.findUserByEmail(username).getLookups());

        }

        model.addAttribute("AUTH_REQUEST", this.userModel);
        model.addAttribute("USER_INPUT", this.inputModel);
        model.addAttribute("BARCODE", this.inputModel.getBarcode());

        model.addAttribute("ISVALID", this.isValid);

        return "home";
    }

    @PostMapping
    public String validateBarcode(Model model, @ModelAttribute("USER_INPUT") InputModel inputModel) {

        this.inputModel = inputModel;

        this.isValid = validatorService.validateBarcode(this.inputModel.getBarcode()).isValid();

        return "redirect:/";
    }
}