package com.urosmitrasinovic61017.planinarski_klub_webapp.services;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Narudzbenica;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.StavkaKorpe;

import java.util.List;
import java.util.Optional;

public interface NarudzbenicaService {

    List<Narudzbenica> getAllNarudzbenice();

    Narudzbenica save(Narudzbenica narudzbenica);

    Narudzbenica update(Narudzbenica narudzbenica);

    void deleteById(Integer narudzbenicaId);

    Optional<Narudzbenica> findById(Integer narudzbenicaId);

    List<Narudzbenica> getAllNarudzbeniceKorisnika(Korisnik korisnik);

    List<Narudzbenica> getAllNarudzbeniceKorisnikaUPoslednjihMesecDana(Integer korisnikId);

    List<Narudzbenica> getAllNarudzbeniceKorisnikaStarijihOdMesecDana(Integer korisnikId);

}
