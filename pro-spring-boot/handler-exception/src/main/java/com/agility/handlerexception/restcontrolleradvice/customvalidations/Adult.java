package com.agility.handlerexception.restcontrolleradvice.customvalidations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = AdultValidator.class)
public @interface Adult {
    String message() default "{adult}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
