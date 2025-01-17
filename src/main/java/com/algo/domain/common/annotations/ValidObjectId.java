package com.algo.domain.common.annotations;

import com.algo.domain.common.validators.ObjectIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@ReportAsSingleViolation
@Constraint(
        validatedBy = {ObjectIdValidator.class}
)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface ValidObjectId {
    String message() default "Invalid hexadecimal representation of an ObjectId";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}