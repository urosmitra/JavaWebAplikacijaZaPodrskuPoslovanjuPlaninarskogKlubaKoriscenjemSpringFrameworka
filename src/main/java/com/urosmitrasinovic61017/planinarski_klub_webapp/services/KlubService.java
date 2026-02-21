package com.urosmitrasinovic61017.planinarski_klub_webapp.services;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Klub;

import java.util.List;
import java.util.Optional;

public interface KlubService {

    List<Klub> getAllKlubovi();

    Klub save(Klub klub);

    Klub update(Klub klub);

    void deleteById(Long idKluba);

    Optional<Klub> findById(Long idKluba);
}
