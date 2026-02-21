package com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations;



import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.ProveraBrojaPrijavljenihConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProveraBrojaPrijavljenihConstraintValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProveraBrojaPrijavljenih {

    String message() default "Ne možete izvršiti rezervaciju jer je popunjen maksimalan broj mesta dozvoljenih u okviru ovog aranžmana";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
