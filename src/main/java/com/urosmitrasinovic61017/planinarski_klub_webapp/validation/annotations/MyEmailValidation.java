package com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// /^([a-z\d\.-]+)@([a-z\d-]+)\.([a-z]{2,8})(\.[a-z]{2,8})?$/
@Email(message = "Neispravan format email adrese")
@Pattern(regexp = "^([a-z\\d\\.-]+)@([a-z\\d-]+)\\.([a-z]{2,8})(\\.[a-z]{2,8})?$", message = "Neispravan format email adrese")
@Constraint(validatedBy = {})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyEmailValidation {

    public String message() default "Neispravan format email adrese";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};


}
