package com.urosmitrasinovic61017.planinarski_klub_webapp.services;



import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.RezervacijaPutovanja;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RezervacijaPutovanjeService {

    List<RezervacijaPutovanja> getAllRezervacije();

    RezervacijaPutovanja save(RezervacijaPutovanja rezervacija);

    RezervacijaPutovanja update(RezervacijaPutovanja rezervacija);

    void deleteById(Integer id);

    Optional<RezervacijaPutovanja> findById(Integer id);

    List<RezervacijaPutovanja> getAllRezervacijePutovanja(Integer putovanjeId);

    void setStatusRezervacijaPutovanjeOtkazano(List<RezervacijaPutovanja> listaRezervacija);

    void setStatusRezervacijaPutovanjeOdrzano(List<RezervacijaPutovanja> listaRezervacija);

    boolean daLiKorisnikImaRezervaciju(List<RezervacijaPutovanja> listaRezervacija, Korisnik korisnik);

    List<RezervacijaPutovanja> getAllRezervacijePutovanjaKorisnikaAktivne(Integer korisnikId);

    List<RezervacijaPutovanja> getAllRezervacijaPutovanjaKorisnikaIstorija(Integer korisnikId);

    Integer izracunajCenuPutovanja(Integer popustPutovanja, Integer cenaAranzmana);

}
