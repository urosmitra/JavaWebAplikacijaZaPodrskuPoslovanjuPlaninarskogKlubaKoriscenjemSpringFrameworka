package com.urosmitrasinovic61017.planinarski_klub_webapp.services;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Uloga;

import java.util.List;
import java.util.Optional;

public interface UlogaService {

    List<Uloga> getAllUloge();

    Uloga save(Uloga uloga);

    Uloga update(Uloga uloga);

    void deleteById(Integer id);

    Optional<Uloga> findById(Integer id);

    Optional<Uloga> findByNaziv(String naziv);



}
