package com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations;


import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.NewPasswordMatchesValidator;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.OldPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NewPasswordMatchesValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NewPasswordMatches {

    String message() default "Niste potvrdili novi password, molimo ukucajte ponovo novi password i potvrdite ga u narednom polju";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
