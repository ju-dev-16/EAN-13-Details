package de.judev.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BarcodeDetailsModel {

    private String title;

    private String alias;

    private String description;

    private String brand;

    private String category;

    private String msrp;

    private String barcode;
    
}