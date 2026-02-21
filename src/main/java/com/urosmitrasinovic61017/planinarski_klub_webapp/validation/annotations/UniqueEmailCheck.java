package com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations;

import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.UniqueEmailConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueEmailConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmailCheck {


    //define default error message
    public String message() default "Email koji ste uneli je zauzet";

    //define default groups (CAN GROUP RELATED CONSTRAINTS)
    public Class<?>[] groups() default {};

    //define default payloads (PROVIDE CUSTOM DETAILS ABOUT VALIDATION FAILURE --> npr. severity level, error code etc.)
    public Class<? extends Payload>[] payload() default {};

}
