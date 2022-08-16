package de.judev.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import de.judev.app.data.Barcode;
import de.judev.app.service.helper.Converter;

@Service
public class BarcodeValidatorService {
 
    private final Converter converter = new Converter();

    public Barcode validateBarcode(String input) {

        try {
            // Parsing check digit from barcode
            int checkDigit = Integer.parseInt(input.substring(input.length() - 1)); 
            
            String barcode = input.substring(0, input.length() - 1);
    
            if (barcode.length() != 12) {
                return new Barcode(input, false);
            }
    
            // Multiply the barcode as list 3 times
            List<Integer> multipliedBarcode = multiplyList(barcode, 3);

            List<Integer> checksum = calculateChecksum(multipliedBarcode);

            // Check digit procedure
            if (!checkDigitProcedure(checksum, checkDigit)) {

                return new Barcode(input, false);
                
            }

        } catch (NumberFormatException nfe) {

            return new Barcode(input, false);

        }

        return new Barcode(input, true);
    }

    private List<Integer> multiplyList(String barcode, int faktor) {      

        List<Integer> currentList = converter.strListToIntList(converter.splittStrNumber(barcode));

        List<Integer> multipliedList = new ArrayList<>();

        for (int i = 0; i < currentList.size(); i++) {

            if (i % 2 == 1) {

                multipliedList.add(currentList.get(i));

            } else {

                multipliedList.add(currentList.get(i) * faktor);

            }

        }
        
        return multipliedList;
    }

    private List<Integer> calculateChecksum(List<Integer> numbers) {

        Integer result = 0;

        for (int i = 0; i < numbers.size(); i++) {
            result = result + numbers.get(i);
        }

        return converter.castIntToList(result);
    }

    private boolean checkDigitProcedure(List<Integer> checksum, int checkDigit) {

        if ((Integer.parseInt(converter.intListToStr(checksum)) + checkDigit) % 10 == 0) {

            return true;
    
        } else {
    
            return false;
    
        }   
    }
}