package com.urosmitrasinovic61017.planinarski_klub_webapp.validation;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Aranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.BrojPutnikaMinMaxValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BrojPutnikaConstraintValidator implements ConstraintValidator<BrojPutnikaMinMaxValid, Object> {


    @Override
    public void initialize(BrojPutnikaMinMaxValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Aranzman aranzman = (Aranzman) o;
        Integer minPutnika = aranzman.getMinPutnika();
        Integer maxPutnika = aranzman.getMaxPutnika();

        return maxPutnika > minPutnika; //MAX PUTNIKA POLJE MORA BITI VECE OD MIN PUTNIKA POLJE

    }
}
