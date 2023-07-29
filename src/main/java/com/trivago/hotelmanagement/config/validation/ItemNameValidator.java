package com.trivago.hotelmanagement.config.validation;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ItemNameValidator implements ConstraintValidator<ItemNameConstraint, String> {
    @Value("${hotel-management.unaccepted-item-names}")
    private List<String> unacceptedItemNames;

    @Override
    public void initialize(ItemNameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        // Assuming that name is required field since it should contain more than 10 characters.
        if (StringUtils.isEmpty(name) || name.length() < 10) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Should not be empty and must have atleast 10 characters.").addConstraintViolation();
            return false;
        }

        if (CollectionUtils.isEmpty(unacceptedItemNames)) {
            return true;
        }

        for (String unacceptedName : unacceptedItemNames) {
            if (name.toLowerCase().contains(unacceptedName.toLowerCase())) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Can not contain one of: " + unacceptedItemNames.toString()).addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
