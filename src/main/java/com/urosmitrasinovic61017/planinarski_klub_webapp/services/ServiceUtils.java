package com.urosmitrasinovic61017.planinarski_klub_webapp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServiceUtils { //Inicijalizuje Servise manuelno, izbegavajuci Dependency Injection (@Autowired automatski nacin injectovanja servisa)
    //treba nam kod UniqueEmailConstraintValidator-a zbog toga sto hibernate validator factory ne injectuje
    //servise u ConstraintValidator klasu

    private static ServiceUtils instance;

    @Autowired
    private KorisnikService korisnikService;


    @PostConstruct
    public void fillInstance(){
        instance = this;
    }


    /* static methods */

    public static KorisnikService getKorisnikService(){
        return  instance.korisnikService;
    }



}
