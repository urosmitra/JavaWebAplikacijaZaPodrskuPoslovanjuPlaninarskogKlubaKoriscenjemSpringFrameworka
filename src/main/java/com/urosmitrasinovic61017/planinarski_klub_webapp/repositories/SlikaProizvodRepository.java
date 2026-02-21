package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.SlikaProizvod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlikaProizvodRepository extends JpaRepository<SlikaProizvod, Integer> {


    List<SlikaProizvod> findByProizvod(Proizvod proizvod);

}
