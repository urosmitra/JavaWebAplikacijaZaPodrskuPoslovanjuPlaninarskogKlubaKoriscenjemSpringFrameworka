package com.urosmitrasinovic61017.planinarski_klub_webapp.services;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Narudzbenica;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.StavkaKorpe;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.StavkaNarudzbenice;

import java.util.List;
import java.util.Optional;

public interface StavkaNarudzbeniceService {

    List<StavkaNarudzbenice> getAllStavkeNarudzbenice();

    StavkaNarudzbenice save(StavkaNarudzbenice stavkaNar);

    StavkaNarudzbenice update(StavkaNarudzbenice stavkaNar);

    void deleteById(Integer stavkaNarudzbeniceId);

    Optional<StavkaNarudzbenice> findById(Integer stavkaNarId);

    List<StavkaNarudzbenice> findAllByNarudzbenica(Narudzbenica narudzbenica);

    List<StavkaNarudzbenice> findAllByProizvod(Proizvod proizvod);

    void saveStavkeNarudzbeniceAndUpdateKolicinuProizvoda(Integer narudzbenicaId, List<StavkaKorpe> listaStavkiKorpe);


}
