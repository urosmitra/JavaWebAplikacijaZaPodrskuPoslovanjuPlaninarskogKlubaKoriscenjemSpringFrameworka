package com.urosmitrasinovic61017.planinarski_klub_webapp.validation;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.KorisnikChangePassDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.NewPasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NewPasswordMatchesValidator implements ConstraintValidator<NewPasswordMatches, Object> {
    @Override
    public void initialize(NewPasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        KorisnikChangePassDto korChanPassDto = (KorisnikChangePassDto) o;


        return korChanPassDto.getNewPassword().equals(korChanPassDto.getNewPasswordMatch());
    }
}
