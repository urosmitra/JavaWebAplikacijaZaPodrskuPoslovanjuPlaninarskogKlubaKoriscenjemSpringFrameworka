package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.BuducaPutovanjaIzvestajDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ProslaPutovanjaIzvestajDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Putovanje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PutovanjeRepository extends JpaRepository<Putovanje, Integer> {

    //pronadji sva putovanja za odredjeni aranzman
    List<Putovanje> findByAranzmanAranzmanId(Integer aranzmanId);
    
    //pronadji sve preostojece(u buducnosti) polaske/putovanje
    @Query(value = "SELECT p FROM Putovanje p WHERE p.aranzman.aranzmanId = :aranzmanId AND p.datumVremePolaska >= CURRENT_DATE" +
            " ORDER BY p.datumVremePolaska ASC")
    List<Putovanje> getAllBuducaPutovanjaAranzmana(@Param("aranzmanId") Integer aranzmanId);

    //pronadji putovanje koje se trenutno realizuje (Dakle datum i vreme polaska je prosli datum, a datum povratka je buduci datum)
    @Query(value = "SELECT p FROM Putovanje p WHERE p.aranzman.aranzmanId = :aranzmanId AND p.datumVremePolaska <= CURRENT_TIMESTAMP AND p.datumPovratka >= CURRENT_DATE AND (p.status = 'POTVRDJENO' OR p.status = 'U_TOKU') " +
            "ORDER BY p.datumVremePolaska ASC")
    List<Putovanje> getAllPutovanjaAranzmanaUTokuPotvrdjeno(@Param("aranzmanId") Integer aranzmanId);



    //pronadji istoriju putovanja aranzmana
    @Query(value = "SELECT p FROM Putovanje p WHERE p.aranzman.aranzmanId = :aranzmanId AND p.datumPovratka < current_date AND (p.status = 'POTVRDJENO' OR p.status = 'U_TOKU' OR p.status = 'ODRZANO') " +
            "ORDER BY p.datumVremePolaska ASC")
    List<Putovanje> getIstorijaPutovanjaOdrzanih(@Param("aranzmanId") Integer aranzmanId);

    @Query(value = "SELECT p FROM Putovanje p WHERE p.aranzman.aranzmanId = :aranzmanId AND p.datumPovratka < CURRENT_DATE AND p.status = 'NEPOTVRDJENO'")
    List<Putovanje> getAllPutovanjaNepotvrdjenaProsla(@Param("aranzmanId") Integer aranzmanId);


    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE putovanje p SET p.status = 'ODRZANO' WHERE p.putovanjeId = :putovanjeId", nativeQuery = true)
    void podesavanjeStatusaPutovanjaOdrzanih(@Param("putovanjeId") Integer putovanjeId);


    @Query(value = "SELECT p FROM Putovanje p WHERE p.aranzman.aranzmanId = :aranzmanId AND p.status = 'OTKAZANO' " +
            "ORDER BY p.datumVremePolaska ASC")
    List<Putovanje> getOtkazanaPutovanjaAranzmana(@Param("aranzmanId") Integer aranzmanId);


    /*@Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE putovanje p SET p.potvrdjeno = false WHERE p.putovanjeId = :putovanjeId", nativeQuery = true)
    void updatePotvrdjenoPutovanjeFalse(@Param("putovanjeId") Integer putovanjeId);*/


    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE putovanje p SET p.status = 'U_TOKU' WHERE p.putovanjeId = :putovanjeId", nativeQuery = true)
    void podesavanjeStatusaPutovanjaUToku(@Param("putovanjeId") Integer putovanjeId);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE putovanje p SET p.status = 'OTKAZANO' WHERE p.putovanjeId = :putovanjeId", nativeQuery = true)
    void podesavanjeStatusaPutovanjaOtkazano(@Param("putovanjeId") Integer putovanjeId);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE putovanje p SET p.status = 'POTVRDJENO' WHERE p.putovanjeId = :putovanjeId", nativeQuery = true)
    void podesavanjeStatusaPutovanjaPotvrdjeno(@Param("putovanjeId") Integer putovanjeId);


    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE putovanje p SET p.status = 'NEPOTVRDJENO' WHERE p.putovanjeId = :putovanjeId", nativeQuery = true)
    void podesavanjeStatusaPutovanjaNepotvrdjeno(@Param("putovanjeId") Integer putovanjeId);



    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value ="UPDATE putovanje p SET p.brojPrijavljenih = p.brojPrijavljenih + 1 WHERE p.putovanjeId = :putovanjeId", nativeQuery = true)
    void incrementBrojPrijavljenih(@Param("putovanjeId") Integer putovanjeId);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value ="UPDATE putovanje p SET p.brojPrijavljenih = p.brojPrijavljenih - 1 WHERE p.putovanjeId = :putovanjeId", nativeQuery = true)
    void smanjiBrojPrijavljenih(@Param("putovanjeId") Integer putovanjeId);

    //pronadji sve preostojece(u buducnosti) polaske/putovanje
    /*@Query(value = "SELECT p.* FROM putovanje p JOIN aranzman a ON p.aranzmanId = a.aranzmanId WHERE DATE(p.datumVremePolaska) > CURDATE() " +
            "AND p.brojPrijavljenih < a.max_putnika AND p.status != 'OTKAZANO' ORDER BY DATE(p.datumVremePolaska) ASC", nativeQuery = true)
    List<Putovanje> getBuducaPutovanjaLimitirano();*/

    @Query(value = "SELECT p FROM Putovanje p JOIN p.aranzman a WHERE p.datumVremePolaska > CURRENT_DATE " +
            "AND p.brojPrijavljenih < a.maxPutnika AND p.status != 'OTKAZANO' ORDER BY p.datumVremePolaska ASC")
    List<Putovanje> getBuducaPutovanjaLimitirano();

    @Query(value = "SELECT p FROM Putovanje p JOIN p.aranzman a WHERE a.aranzmanId = :aranzmanId " +
            "AND p.datumVremePolaska > CURRENT_DATE AND p.brojPrijavljenih < a.maxPutnika AND p.status != 'OTKAZANO' ORDER BY p.datumVremePolaska ASC")
    List<Putovanje> getAllBuducaPutovanjaAranzmanaAvailableZaKorisnika(@Param("aranzmanId") Integer aranzmanId);

    @Query(value = "SELECT new com.urosmitrasinovic61017.planinarski_klub_webapp.models.BuducaPutovanjaIzvestajDto(p.putovanjeId, a.naziv as nazivPutovanja, p.datumVremePolaska, p.datumPovratka, a.cena as cena, p.popust, p.cenaSaPopustom, p.brojPrijavljenih as brojRezervacija, SUM(rez.cenaPutovanja) as projekcijaZarade, p.popunjenostPutovanja)" +
            " FROM Putovanje p  JOIN p.aranzman a LEFT JOIN p.listaRezervacija rez WHERE p.datumVremePolaska >= CURRENT_DATE GROUP BY p.putovanjeId")
    Page<BuducaPutovanjaIzvestajDto> getAllBuducaPutovanjaIzvestajZaMenadzera(Pageable pageable);

    @Query(value = "SELECT new com.urosmitrasinovic61017.planinarski_klub_webapp.models.ProslaPutovanjaIzvestajDto(p.putovanjeId, a.naziv as nazivPutovanja, p.datumVremePolaska, p.datumPovratka, p.brojPrijavljenih, p.status, p.prosecnaZaradaPoPutniku as  prosecnaZaradaPoPutniku, p.ukupnaZarada as ukupnaZarada, p.popunjenostPutovanja, p.izvestaj)" +
            " FROM Putovanje p JOIN p.aranzman a LEFT JOIN p.listaRezervacija rez WHERE p.datumPovratka < CURRENT_DATE GROUP BY p.putovanjeId")
    Page<ProslaPutovanjaIzvestajDto> getAllProslaPutovanjaIzvestajZaMenadzera(Pageable pageable);


}