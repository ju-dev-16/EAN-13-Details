package de.judev.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.judev.app.data.AuthRequest;
import de.judev.app.data.BarcodeDetails;
import de.judev.app.data.UserInput;
import de.judev.app.service.BarcodeDetailsService;
import de.judev.app.service.BarcodeValidatorService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    // private final UserService service;
    private final BarcodeValidatorService validatorService;
    private final BarcodeDetailsService detailsService;

    private AuthRequest authRequest = new AuthRequest();
    private UserInput userInput = new UserInput();
    
    private boolean isValid = false;

    @GetMapping("/")
    public String showHome(Model model) {

        model.addAttribute("AUTH_REQUEST", this.authRequest);
        model.addAttribute("USER_INPUT", this.userInput);
        model.addAttribute("BARCODE", this.userInput.getBarcode());

        model.addAttribute("ISVALID", this.isValid);

        return "home";
    }

    @PostMapping("/")
    public String validateBarcode(Model model, @ModelAttribute("USER_INPUT") UserInput userInput) {

        this.userInput = userInput;

        this.isValid = validatorService.validateBarcode(this.userInput.getBarcode()).isValid();

        return "redirect:/";
    }

    @PostMapping("/details")
    public String showDetails(Model model, @ModelAttribute("USER_INPUT") UserInput userInput) {

        BarcodeDetails details = detailsService.getBarcodeDetails(userInput.getBarcode());

        model.addAttribute("DETAILS", details);

        return "details";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("AUTH_REQUEST") AuthRequest authRequest) {

        return "redirect:/";
    }

    @PostMapping("/login-error")
    public String loginError(Model model) {

        model.addAttribute("loginError", true);

        return "home";
    }
}