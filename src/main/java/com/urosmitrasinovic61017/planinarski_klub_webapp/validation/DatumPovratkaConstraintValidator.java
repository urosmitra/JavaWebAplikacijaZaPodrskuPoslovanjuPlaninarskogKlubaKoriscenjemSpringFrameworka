package com.urosmitrasinovic61017.planinarski_klub_webapp.validation;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Putovanje;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.DatumPovratkaValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DatumPovratkaConstraintValidator implements ConstraintValidator<DatumPovratkaValid, Object> {
    @Override
    public void initialize(DatumPovratkaValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        Putovanje p = (Putovanje) o;

        //.....

        return false;
    }
}
