package de.judev.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.judev.web.model.BarcodeDetailsModel;
import de.judev.web.model.InputModel;
import de.judev.web.service.BarcodeDetailsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/details")
@RequiredArgsConstructor
public class DetailsController {
    
    private final BarcodeDetailsService detailsService;

    @PostMapping
    public String showDetails(Model model, @ModelAttribute("USER_INPUT") InputModel inputModel) {

        BarcodeDetailsModel details = detailsService.getBarcodeDetails(inputModel.getBarcode());

        model.addAttribute("DETAILS", details);

        return "details";
    }
}