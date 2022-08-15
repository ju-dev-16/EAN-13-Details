package de.judev.app.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Barcode {
    
    private String barcode;

    private boolean isValid;

}