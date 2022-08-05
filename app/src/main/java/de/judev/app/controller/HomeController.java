package de.judev.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.judev.app.model.BarcodeDetails;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("barcodeDetails", new BarcodeDetails());
        model.addAttribute("barcode", "4073842571641");

        return "home";
    }
}