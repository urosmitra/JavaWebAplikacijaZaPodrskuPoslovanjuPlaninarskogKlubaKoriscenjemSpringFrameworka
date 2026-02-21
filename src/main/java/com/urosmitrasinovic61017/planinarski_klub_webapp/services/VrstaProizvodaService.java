package com.urosmitrasinovic61017.planinarski_klub_webapp.services;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.VrstaProizvoda;

import java.util.List;
import java.util.Optional;

public interface VrstaProizvodaService {

    List<VrstaProizvoda> getAllVrsteProizvoda();

    VrstaProizvoda save(VrstaProizvoda vrstaProizvoda);

    VrstaProizvoda update(VrstaProizvoda vrstaProizvoda);

    void deleteById(Integer vrstaProizvodaId);

    Optional<VrstaProizvoda> findById(Integer vrstaProizvodaId);


}
