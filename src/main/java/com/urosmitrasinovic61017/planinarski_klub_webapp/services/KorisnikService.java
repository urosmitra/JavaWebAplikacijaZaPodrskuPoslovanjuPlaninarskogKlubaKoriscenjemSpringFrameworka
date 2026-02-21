package com.urosmitrasinovic61017.planinarski_klub_webapp.services;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.KorisnikChangePassDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.RegistracijaDTO;

import java.util.List;
import java.util.Optional;

public interface KorisnikService {

    List<Korisnik> getAllKorisnici();

    Korisnik save(Korisnik korisnik);

    Korisnik update(Korisnik korisnik);

    void deleteById(Integer id);

    Optional<Korisnik> findById(Integer id);

    Optional<Korisnik> findByEmail(String email);

    Korisnik registracijaKorisnika(RegistracijaDTO registracijaDTO);

    List<Korisnik> findAllByKeyword(String keyword);

    void izmenaKorisnickogNaloga(Korisnik korisnik);

    void changeKorisnikPassword(Korisnik korisnik, KorisnikChangePassDto korisnikChangePassDto);

}
