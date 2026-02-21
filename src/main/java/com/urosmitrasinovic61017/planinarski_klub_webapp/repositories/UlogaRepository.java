package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Uloga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UlogaRepository extends JpaRepository<Uloga, Integer> {

    Optional<Uloga> findByNaziv(String nazivUloge);

}
