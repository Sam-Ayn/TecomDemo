package ru.tecom.test.demo.validator;

import org.springframework.stereotype.Service;

@Service
public class SearchFieldsValidator {

    public boolean validateCarField(String field){
        if (field == null) return false;
        switch (field) {
            case "id" : return true;
            case "brand" : return true;
            case "model" : return true;
            case "year" : return true;
            case "month" : return true;
            case "engineDisplacement" : return true;
            case "turbo" : return true;
            case "power" : return true;
            case "transmissionType" : return true;
            case "driveType" : return true;
            case "bodyType" : return true;
            case "color" : return true;
            default: return false;
        }
    }
}