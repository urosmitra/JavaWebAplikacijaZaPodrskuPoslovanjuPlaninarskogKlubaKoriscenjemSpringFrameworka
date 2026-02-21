package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Narudzbenica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NarudzbenicaRepository extends JpaRepository<Narudzbenica, Integer> {

    List<Narudzbenica> findByKorisnik(Korisnik korisnik);


    @Query(value = "SELECT n.* FROM narudzbenica n WHERE n.korisnikId = :korisnikId AND (n.datumVreme BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW()) " +
            "ORDER BY n.datumVreme DESC", nativeQuery = true)
    List<Narudzbenica> getAllNarudzbeniceKorisnikaUPoslednjihMesecDana(@Param("korisnikId") Integer korisnikId);

    @Query(value = "SELECT n.* FROM narudzbenica n WHERE n.korisnikId = :korisnikId AND n.datumVreme < DATE_SUB(NOW(), INTERVAL 30 DAY) ORDER BY n.datumVreme DESC", nativeQuery = true)
    List<Narudzbenica> getAllNarudzbeniceKorisnikaStarijihOdMesecDana(@Param("korisnikId") Integer korisnikId);
}
