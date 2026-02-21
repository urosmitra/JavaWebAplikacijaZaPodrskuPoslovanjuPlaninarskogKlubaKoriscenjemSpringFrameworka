package com.urosmitrasinovic61017.planinarski_klub_webapp.services;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.StavkaKorpe;

import java.util.List;
import java.util.Optional;

public interface StavkaKorpeService {

    List<StavkaKorpe> getAllStavkeKorpe();

    StavkaKorpe save(StavkaKorpe stavkaKorpe);

    StavkaKorpe update(StavkaKorpe stavkaKorpe);

    void deleteById(Integer stavkaKorpeId);

    Optional<StavkaKorpe> findById(Integer stavkaKorpeId);

    List<StavkaKorpe> getAllStavkeKorpeKorisnika(Korisnik korisnik);

    Integer izracunajUkupnuCenuStavkeKorpe(StavkaKorpe stavkaKorpe);

    Integer izracunajTotalCenuKorpe(List<StavkaKorpe> listaStavkiKorpe);

    List<StavkaKorpe> getAllStavkeKorpeNemaNaStanju(Korisnik korisnik);

    StavkaKorpe findByKorisnikAndProizvod(Korisnik korisnik, Proizvod proizvod);
    //dodajProizvodUKorpu(proizvod, novaStavkaKorpe, korisnik);

    Integer dodajProizvodUKorpu(StavkaKorpe novaStavkaKorpe);
}
