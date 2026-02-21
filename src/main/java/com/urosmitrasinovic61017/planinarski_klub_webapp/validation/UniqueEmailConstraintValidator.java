package com.urosmitrasinovic61017.planinarski_klub_webapp.validation;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KorisnikService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ServiceUtils;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.UniqueEmailCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmailCheck, String> {

    @Autowired
    private KorisnikService korisnikService;

    @Override
    public void initialize(UniqueEmailCheck constraintAnnotation) {
        korisnikService = ServiceUtils.getKorisnikService();
    }


    @Override
    public boolean isValid(String emailSaForme, ConstraintValidatorContext constraintValidatorContext) {

        //PROVERA DA LI KORISNIK SA UKUCANIM EMAIL-OM VEC POSTOJI?
        Optional<Korisnik> korisnik = this.korisnikService.findByEmail(emailSaForme);

        if(korisnik.isPresent()){
            return true;
        }
        return  false;

    }
}
