package de.judev.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.judev.web.model.InputModel;
import de.judev.web.model.UserModel;
import de.judev.web.service.BarcodeValidatorService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final BarcodeValidatorService validatorService;

    private UserModel userModel = new UserModel();
    private InputModel inputModel = new InputModel();
    
    private boolean isValid = false;
    
    @GetMapping
    public String showHome(Model model) {

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