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
    private String isValid;
    private String icon;
    private String fill;

    private BarcodeValidatorService service = new BarcodeValidatorService();

    public MainController() {

        this.isValid = "";
        this.icon = "";
        this.fill = "";
    }

    @GetMapping("/")
    public String showHome(Model model) {

        model.addAttribute("USER_INPUT", this.userInput);
        model.addAttribute("BARCODE", this.userInput.getBarcode());

        model.addAttribute("ISVALID", this.isValid);
        model.addAttribute("ICON", this.icon);
        model.addAttribute("FILL", this.fill);

        return "home";
    }

    @PostMapping("/")
    public String validateFormData(Model model, @ModelAttribute("USER_INPUT") UserInput userInput) {

        this.userInput = userInput;

        if (service.validateBarcode(this.userInput.getBarcode()).isValid()) {

            this.isValid = "is valid.";
            this.icon = "M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z";
            this.fill = "lightgreen";

        } else {

            this.isValid = "is unvalid.";
            this.icon = "M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z";
            this.fill = "red";

        }

        return "redirect:/";
    }

    @GetMapping("/details")
    public String showDetails(Model model) {

        return "details";
    }
}