package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {

    Optional<Korisnik> findByEmail(String email);

    @Query(value = "SELECT k.* FROM korisnik k WHERE k.email LIKE %:keyword% OR k.ime LIKE %:keyword% OR k.prezime LIKE %:keyword%", nativeQuery = true)
    List<Korisnik> findByKeyword(@Param("keyword") String keyword);

}
