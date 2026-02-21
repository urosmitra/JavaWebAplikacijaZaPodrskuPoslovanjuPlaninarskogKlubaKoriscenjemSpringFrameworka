package com.urosmitrasinovic61017.planinarski_klub_webapp.validation;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.RegistracijaDTO;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RegistracijaDTO reg = (RegistracijaDTO) o;
        return reg.getPassword().equals(reg.getMatchingPassword());
    }
}
