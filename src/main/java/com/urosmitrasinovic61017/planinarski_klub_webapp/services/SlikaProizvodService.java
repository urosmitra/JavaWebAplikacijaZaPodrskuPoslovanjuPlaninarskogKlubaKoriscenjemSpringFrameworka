package com.urosmitrasinovic61017.planinarski_klub_webapp.services;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.SlikaProizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.NazivSlikeProizvodaAlreadyExists;

import java.util.List;
import java.util.Optional;

public interface SlikaProizvodService {

    List<SlikaProizvod> getAllSlikeSvihProizvoda();

    SlikaProizvod save(SlikaProizvod slikaProizvod) throws NazivSlikeProizvodaAlreadyExists;

    SlikaProizvod update(SlikaProizvod slikaProizvod) throws NazivSlikeProizvodaAlreadyExists;

    void deleteById(Integer slikaProizvodId);

    Optional<SlikaProizvod> findById(Integer slikaProizvodId);

    List<SlikaProizvod> getAllSlikeProizvoda(Proizvod proizvod);

}
