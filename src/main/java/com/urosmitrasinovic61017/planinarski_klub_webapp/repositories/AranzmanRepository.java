package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Aranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.AranzmanIzvestajDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AranzmanRepository extends JpaRepository<Aranzman, Integer> {

    Optional<Aranzman> findByNaziv(String naziv);

    List<Aranzman> findByVrstaPutovanjaVrstaPutovanjaId(Integer vrstaPutovanjaId);

    @Query(value = "SELECT new com.urosmitrasinovic61017.planinarski_klub_webapp.models.AranzmanIzvestajDto(a.aranzmanId, a.naziv as nazivAranzmana, a.thumbnailPhoto, a.cena, SUM(pt.brojPrijavljenih) as brojPutnikaZaAranzman, AVG(pt.popunjenostPutovanja) as prosekPopunjenostiAranzmana, SUM(pt.ukupnaZarada) as ukupnaZaradaAranzmana, a.vrstaPutovanja as vrstaPutovanjaAranzmana, AVG(pt.prosecnaZaradaPoPutniku) as prosecnaZaradaPoPutnikuAranzmana)" +
            " FROM Aranzman a LEFT JOIN a.listaPutovanja pt WHERE (pt.datumPovratka < CURRENT_DATE AND pt.status NOT IN ('OTKAZANO', 'NEPOTVRDJENO')) OR pt is null GROUP BY a.aranzmanId")
    Page<AranzmanIzvestajDto> getAllAranzmanIzvestajiZaMenadzera(Pageable pageable);


}
