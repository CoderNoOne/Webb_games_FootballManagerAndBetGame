package org.example.model.core.enums;

import lombok.extern.slf4j.Slf4j;
import org.example.core.exceptions.AppException;

import java.text.MessageFormat;
import java.util.Arrays;

@Slf4j
public enum AuthorityDto {
    ROLE_USER, ROLE_ADMIN;

    public static AuthorityDto fromString(String value) {
        if (value == null) {
            throw new AppException("Enum value is null");
        }

        try {
            return AuthorityDto.valueOf(value);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new AppException(MessageFormat.format("No enum constant for value: {0}", value));
        }
    }
}
