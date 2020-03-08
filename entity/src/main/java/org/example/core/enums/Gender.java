package org.example.core.enums;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.AppException;

import java.text.MessageFormat;
import java.util.Arrays;

@Slf4j
public enum Gender {
    MALE, FEMALE;

    public static Gender fromString(String name) {

        if (name == null) {
            throw new AppException("Name is null");
        }

        try {
            return Gender.valueOf(name.toUpperCase());
        } catch (Exception e) {

            log.info(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new AppException(MessageFormat.format("Gender value {0} not an enum constant", name));
        }

    }
}
