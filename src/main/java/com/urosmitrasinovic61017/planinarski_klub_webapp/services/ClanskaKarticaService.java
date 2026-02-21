package com.urosmitrasinovic61017.planinarski_klub_webapp.services;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ClanskaKartica;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;

import java.util.List;
import java.util.Optional;

public interface ClanskaKarticaService {

    List<ClanskaKartica> getAllClanskeKartice();

    ClanskaKartica save(ClanskaKartica clanskaKartica);

    ClanskaKartica update(ClanskaKartica clanskaKartica);

    void deleteById(Integer clanskaKarticaId);

    Optional<ClanskaKartica> findById(Integer id);

    //void adminPromenaClanstvaUKlubuKorisnika(Korisnik izmenjenKorisnik);

    List<String> getListaStatusaKartice();

    boolean isActive(ClanskaKartica clanskaKartica);

    void smanjiBrojBodovaNaKartici(ClanskaKartica clanskaKartica, Integer bodovi);

    void povecajBrojBodovaNaKarticiPriPorudzbini(ClanskaKartica clanskaKartica);



}
