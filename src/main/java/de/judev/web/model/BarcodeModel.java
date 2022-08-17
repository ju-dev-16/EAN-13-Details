package de.judev.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BarcodeModel {
    
    private String barcode;

    private boolean isValid;

}