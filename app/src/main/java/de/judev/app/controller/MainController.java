package de.judev.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.judev.app.model.BarcodeDetails;
import de.judev.app.model.UserInput;
import de.judev.app.service.BarcodeDetailsService;
import de.judev.app.service.BarcodeValidatorService;

@Controller
public class MainController {
    
    private UserInput userInput;
    private String isValid;

    private BarcodeValidatorService validatorService;
    private BarcodeDetailsService detailsService;

    public MainController() {

        this.userInput = new UserInput();
        this.isValid = "";

        this.validatorService = new BarcodeValidatorService();
        this.detailsService = new BarcodeDetailsService();
    }

    @GetMapping("/")
    public String showHome(Model model) {

        model.addAttribute("USER_INPUT", this.userInput);
        model.addAttribute("BARCODE", this.userInput.getBarcode());

        model.addAttribute("ISVALID", this.isValid);

        return "home";
    }

    @PostMapping("/")
    public String validateFormData(Model model, @ModelAttribute("USER_INPUT") UserInput userInput) {

        this.userInput = userInput;

        if (validatorService.validateBarcode(this.userInput.getBarcode()).isValid()) {

            this.isValid = "is valid.";

        } else {

            this.isValid = "is unvalid.";

        }

        return "redirect:/";
    }

    @PostMapping("/details")
    public String showDetails(Model model, @ModelAttribute("USER_INPUT") UserInput userInput) {

        BarcodeDetails details = detailsService.getBarcodeDetails(userInput.getBarcode());

        model.addAttribute("DETAILS", details);

        return "details";
    }
}