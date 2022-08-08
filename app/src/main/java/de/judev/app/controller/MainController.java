package de.judev.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.judev.app.model.UserInput;
import de.judev.app.service.BarcodeValidatorService;

@Controller
public class MainController {
    
    private UserInput userInput = new UserInput();

    private BarcodeValidatorService service = new BarcodeValidatorService();

    @GetMapping("/")
    public String showHome(Model model) {

        model.addAttribute("USER_INPUT", userInput);
        model.addAttribute("BARCODE", userInput.getBarcode());

        model.addAttribute("ISVALID", "");

        return "home";
    }

    @PostMapping("/")
    public String validateFormData(Model model, @ModelAttribute("USER_INPUT") UserInput userInput) {

        this.userInput = userInput;

        model.addAttribute("USER_INPUT", this.userInput);
        model.addAttribute("BARCODE", this.userInput.getBarcode());

        if (service.validateBarcode(this.userInput.getBarcode()).isValid()) {

            model.addAttribute("ISVALID", "is valid.");

        } else {

            model.addAttribute("ISVALID", "is not valid.");

        }

        return "redirect:/";
    }

    @GetMapping("/details")
    public String showDetails(Model model) {

        return "details";
    }
}