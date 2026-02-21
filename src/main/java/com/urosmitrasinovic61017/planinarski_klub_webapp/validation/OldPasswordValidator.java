package com.urosmitrasinovic61017.planinarski_klub_webapp.validation;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.KorisnikChangePassDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.OldPasswordVerify;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OldPasswordValidator implements ConstraintValidator<OldPasswordVerify, Object> {


    @Override
    public void initialize(OldPasswordVerify constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        KorisnikChangePassDto korChanPassDto = (KorisnikChangePassDto) o;

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

        return passwordEncoder.matches(korChanPassDto.getOldPasswordInput(), korChanPassDto.getOldPassword());
    }
}
