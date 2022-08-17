package de.judev.web.service.helper;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public List<Integer> castIntToList(int number) {
        return strListToIntList(splittStrNumber(String.valueOf(number)));
    }
    
    public List<String> splittStrNumber(String number) {
    
        List<String> splittedNumber = new ArrayList<>();
    
        for (int i = 0; i < number.length(); i++) {
            splittedNumber.add(String.valueOf(number.charAt(i)));
        }
    
        return splittedNumber;
    } 
    
    public List<Integer> strListToIntList(List<String> list) {
            
        List<Integer> intList = new ArrayList<>();
    
        for (String s : list) {
            intList.add(Integer.parseInt(s));
        }
    
        return intList;
    }

    public String intListToStr(List<Integer> list) {

        StringBuilder stringBuilder = new StringBuilder();
    
        for (Integer i : list) {
            stringBuilder.append(String.valueOf(i));
        }

        return stringBuilder.toString();
    }
}