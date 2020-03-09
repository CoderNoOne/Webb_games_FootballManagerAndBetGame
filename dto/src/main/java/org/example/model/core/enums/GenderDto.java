package org.example.model.core.enums;


import lombok.extern.slf4j.Slf4j;
import org.example.core.exceptions.AppException;

import java.text.MessageFormat;
import java.util.Arrays;

@Slf4j
public enum GenderDto {
    MALE, FEMALE;

    public static GenderDto fromString(String name) {

        if (name == null) {
            throw new AppException("Value is null");
        }

        try {
            return GenderDto.valueOf(name);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new AppException(MessageFormat.format("No enum constant: {0}", name));
        }
    }
}
