package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.StavkaKorpe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StavkaKorpeRepository extends JpaRepository<StavkaKorpe, Integer> {

    List<StavkaKorpe> findByKorisnik(Korisnik korisnik);

    StavkaKorpe findByKorisnikAndProizvod(Korisnik korisnik, Proizvod proizvod);

}
