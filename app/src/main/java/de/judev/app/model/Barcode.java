package de.judev.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Barcode {
    
    private String barcode;

    private boolean isValid;

}