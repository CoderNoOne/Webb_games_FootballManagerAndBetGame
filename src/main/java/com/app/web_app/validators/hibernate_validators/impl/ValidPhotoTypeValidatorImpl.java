package com.app.web_app.validators.hibernate_validators.impl;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.validators.hibernate_validators.annotations.ValidPhotoType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

@Component
public class ValidPhotoTypeValidatorImpl implements ConstraintValidator<ValidPhotoType, MultipartFile> {

    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        try {
            if (file.getBytes().length == 0) {
                return true;
            }

            String contentType = file.getContentType();

            return "image/gif".equals(contentType) || "image/png".equals(contentType)
                    || "image/jpg".equals(contentType) || "image/jpeg".equals(contentType);
        } catch (IOException e) {
            throw new AppException("File validator impl exception: " + e.getMessage());
        }
    }
}
