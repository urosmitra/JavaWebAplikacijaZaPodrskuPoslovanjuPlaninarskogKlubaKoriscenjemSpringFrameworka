package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.RezervacijaPutovanja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RezervacijaPutovanjaRepository extends JpaRepository<RezervacijaPutovanja, Integer> {

    //pronadji sve rezervacije putovanja
    List<RezervacijaPutovanja> findByPutovanjePutovanjeId(Integer putovanjeId);


    //update status putovanja na PUTOVANJE_OTKAZANO
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE rezervacija_putovanja rez SET rez.status = 'PUTOVANJE_OTKAZANO' WHERE rez.rezervacijaId = :rezervacijaId", nativeQuery = true)
     void updateStatusPutovanjeOtkazano(@Param("rezervacijaId") Integer rezervacijaId);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE rezervacija_putovanja rez SET rez.status = 'PUTOVANJE_ODRZANO' WHERE rez.rezervacijaId = :rezervacijaId", nativeQuery = true)
    void updateStatusPutovanjeOdrzano(@Param("rezervacijaId") Integer rezervacijaId);

    @Query(value = "SELECT rez.* FROM rezervacija_putovanja rez JOIN putovanje p ON rez.putovanjeId = p.putovanjeId WHERE rez.korisnikId = :korisnikId " +
            "AND rez.status = 'AKTIVNA' AND DATE(p.datumVremePolaska) > CURDATE() ORDER BY DATE(p.datumVremePolaska) ASC", nativeQuery = true)
    List<RezervacijaPutovanja> getAllRezervacijePutovanjaKorisnikaAktivne(@Param("korisnikId") Integer korisnikId);

    @Query(value = "SELECT rez.* FROM rezervacija_putovanja rez JOIN putovanje p ON rez.putovanjeId = p.putovanjeId WHERE rez.korisnikId = :korisnikId " +
            "AND p.datumPovratka < CURDATE() ORDER BY DATE(p.datumVremePolaska) ASC", nativeQuery = true)
    List<RezervacijaPutovanja> getAllRezervacijaPutovanjaKorisnikaIstorija(@Param("korisnikId") Integer korisnikId);
}
