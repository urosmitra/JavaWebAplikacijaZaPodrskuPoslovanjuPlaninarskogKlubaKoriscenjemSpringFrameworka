package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ProizvodIzvestajDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProizvodRepository extends JpaRepository<Proizvod, Integer> {

    Optional<Proizvod> findByNaziv(String naziv);

    List<Proizvod> findByVrstaProizvodaVrstaProizvodaId(Integer vrstaProizvodaId);

    @Query(value = "SELECT p FROM Proizvod p WHERE p.naziv LIKE %:keyword%")
    List<Proizvod> findByKeyword(@Param("keyword") String keyword);
    //@Formula anotacija radi samo sa jpql querijima, ne sa native querijima nazalost,
    //pogledati zasto nam rade metode orderByPriceAsc i orderByPriceDesc iako su native queriji,
    //pa tako nesto probati za putovanja u native querijima, to je poslednji pokusaj.
    //Ako to ne uspe, vracamo native query za index stranicu, i izbacujemo @Formula anotaciju iz Putovanje entitija

    /*@Query(value = "SELECT p.* FROM proizvod p WHERE p.naziv LIKE %:keyword%", nativeQuery = true)
    List<Proizvod> findByKeyword(@Param("keyword") String keyword);*/

    @Query(value = "SELECT p.*, (p.cena * (1-p.popust/100)) as cenaSaPopustom FROM proizvod p ORDER BY cenaSaPopustom ASC", nativeQuery = true)
    List<Proizvod> orderByPriceAsc();

    @Query(value = "SELECT p.*, (p.cena * (1-p.popust/100)) as cenaSaPopustom FROM proizvod p ORDER BY cenaSaPopustom DESC", nativeQuery = true)
    List<Proizvod> orderByPriceDesc();

    /*@Query(value = "SELECT new com.urosmitrasinovic61017.planinarski_klub_webapp.models.ProizvodIzvestajDto(p.proizvodId, p.naziv, p.cena, p.popust, 0 as cenaSaPopustom,  p.kolicina, SUM(sn.izabranaKolicina), SUM(sn.ukupnaCenaStavke), p.vrstaProizvoda)" +
            " FROM Proizvod p LEFT JOIN p.listaStavkiNarudzbeniceProizvoda sn GROUP BY p.proizvodId")
    List<ProizvodIzvestajDto> getProizvodIzvestajZaMenadzera();*/

    @Query(value = "SELECT new com.urosmitrasinovic61017.planinarski_klub_webapp.models.ProizvodIzvestajDto(p.proizvodId, p.naziv, p.cena, p.popust, p.cenaSaPopustom, p.kolicina as dostupnaKolicina, SUM(sn.izabranaKolicina) as brojProdatihJedinica, SUM(sn.ukupnaCenaStavke) as ukupnaZaradaEvri, p.vrstaProizvoda as vrstaProizvoda)" +
            " FROM Proizvod p LEFT JOIN p.listaStavkiNarudzbeniceProizvoda sn GROUP BY p.proizvodId")
    //@Query(countQuery = "SELECT count(*) FROM proizvod p LEFT JOIN stavka_narudzbenice sn ON p.proizvodId = sn.proizvodId JOIN vrsta_proizvoda vp ON p.vrstaProizvodaId = vp.vrstaProizvodaId GROUP BY p.proizvodId",nativeQuery = true)
    Page<ProizvodIzvestajDto> getProizvodIzvestajZaMenadzeraPagination(Pageable pageable);



}
