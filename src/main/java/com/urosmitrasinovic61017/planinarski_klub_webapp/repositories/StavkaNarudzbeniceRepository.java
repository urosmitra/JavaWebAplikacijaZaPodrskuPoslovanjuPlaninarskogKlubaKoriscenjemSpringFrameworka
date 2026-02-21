package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Narudzbenica;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.StavkaNarudzbenice;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.StavkaNarudzbeniceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StavkaNarudzbeniceRepository extends JpaRepository<StavkaNarudzbenice, Integer> {

    List<StavkaNarudzbenice> findByNarudzbenica(Narudzbenica narudzbenica);

    List<StavkaNarudzbenice> findByProizvod(Proizvod proizvod);

}
