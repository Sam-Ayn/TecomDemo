package ru.tecom.test.demo.validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchFieldsValidatorTest {

    @Autowired
    private SearchFieldsValidator validator;

    @Test
    void validateCarFieldBrand(){
        assertTrue(validator.validateCarField("brand"));
    }

    @Test
    void validateCarFieldUnknown(){
        assertFalse(validator.validateCarField("abc"));
        assertFalse(validator.validateCarField(""));
        assertFalse(validator.validateCarField(null));
    }

}