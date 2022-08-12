package de.judev.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class BarcodeDetails {

    private String title;

    private String alias;

    private String description;

    private String brand;

    private String category;

    private String msrp;

    private String barcode;
    
}