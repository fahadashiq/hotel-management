package com.trivago.hotelmanagement.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ItemNameValidator.class )
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemNameConstraint {
    String message () default "Item name not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
