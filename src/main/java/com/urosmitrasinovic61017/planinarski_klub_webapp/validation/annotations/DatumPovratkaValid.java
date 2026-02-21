package com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations;



import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.DatumPovratkaConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DatumPovratkaConstraintValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatumPovratkaValid {
    String message() default "Datum povratka mora biti u skladu sa trajanjem aranžmana";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
