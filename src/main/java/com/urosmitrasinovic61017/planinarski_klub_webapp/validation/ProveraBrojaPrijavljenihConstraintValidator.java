package com.urosmitrasinovic61017.planinarski_klub_webapp.validation;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Aranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Putovanje;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.RezervacijaPutovanja;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.ProveraBrojaPrijavljenih;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProveraBrojaPrijavljenihConstraintValidator implements ConstraintValidator<ProveraBrojaPrijavljenih, Object> {
    @Override
    public void initialize(ProveraBrojaPrijavljenih constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        //castovanje objekta u klasu
        RezervacijaPutovanja rezervacija = (RezervacijaPutovanja) o;

        Putovanje putovanje = rezervacija.getPutovanje();

        Aranzman aranzman = putovanje.getAranzman();

        return putovanje.getBrojPrijavljenih() < aranzman.getMaxPutnika();
    }
}
