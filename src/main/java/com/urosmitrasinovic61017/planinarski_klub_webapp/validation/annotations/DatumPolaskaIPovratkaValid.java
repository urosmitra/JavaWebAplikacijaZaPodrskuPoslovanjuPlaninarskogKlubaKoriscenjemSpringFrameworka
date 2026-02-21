package com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations;


import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.DatumPolaskaIPovratkaConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DatumPolaskaIPovratkaConstraintValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatumPolaskaIPovratkaValid {

    String message() default "Datum polaska mora biti manji ili jednak datumu povratka";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
