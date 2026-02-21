package com.urosmitrasinovic61017.planinarski_klub_webapp.validation;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Putovanje;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.DatumPolaskaIPovratkaValid;
import org.apache.tomcat.jni.Local;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatumPolaskaIPovratkaConstraintValidator implements ConstraintValidator<DatumPolaskaIPovratkaValid, Object> {

    @Override
    public void initialize(DatumPolaskaIPovratkaValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        //castovanje objekta u klasu
        Putovanje p = (Putovanje) o;
        LocalDate datumPolaska = p.getDatumVremePolaska().toLocalDate();
        LocalDate datumPovratka = p.getDatumPovratka();

        return datumPolaska.isBefore(datumPovratka) || datumPolaska.isEqual(datumPovratka);
    }
}
