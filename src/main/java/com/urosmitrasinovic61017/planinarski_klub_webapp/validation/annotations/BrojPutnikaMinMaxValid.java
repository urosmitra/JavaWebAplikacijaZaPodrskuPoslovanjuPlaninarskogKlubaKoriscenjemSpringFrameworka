package com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations;


import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.BrojPutnikaConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BrojPutnikaConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BrojPutnikaMinMaxValid {

    String message() default "Polje za minimalan broj putnika mora biti manje od polja za maksimalan broj putnika";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
