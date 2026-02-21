package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ClanskaKartica;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.ClanskaKarticaRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.KorisnikRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ClanskaKarticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClanskaKarticaServiceImpl implements ClanskaKarticaService {

    @Autowired
    ClanskaKarticaRepository clanKarticaRepository;

    @Autowired
    KorisnikRepository korisnikRepository;


    @Override
    public List<ClanskaKartica> getAllClanskeKartice() {
        return this.clanKarticaRepository.findAll();
    }

    @Override
    public ClanskaKartica save(ClanskaKartica clanskaKartica) {
        return this.clanKarticaRepository.save(clanskaKartica);
    }

    @Override
    public ClanskaKartica update(ClanskaKartica clanskaKartica) {
        return this.clanKarticaRepository.save(clanskaKartica);
    }

    @Override
    public void deleteById(Integer clanskaKarticaId) {
        this.clanKarticaRepository.deleteById(clanskaKarticaId);
    }

    @Override
    public Optional<ClanskaKartica> findById(Integer id) {
        return this.clanKarticaRepository.findById(id);
    }


   /* @Override
    public void adminPromenaClanstvaUKlubuKorisnika(Korisnik izmenjenKorisnik) {


        Optional<ClanskaKartica> clanskaKarticaKor = this.clanKarticaRepository.findById(izmenjenKorisnik.getKorisnikId());

        //ukoliko korisnik nije clan kluba, a prethodno je bio (ZNACI dogodila se promena), onda obrisati clansku karticu koju je imao
        //dakle ukoliko izmenjen korisnik nije clan i ukoliko ima clansku karticu, obrisati karticu
        if(izmenjenKorisnik.getClanKluba().equals("NE") && clanskaKarticaKor.isPresent()){

            //this.deleteById(izmenjenKorisnik.getKorisnikId());
            System.out.println("uslo u petlju za brisanje 1");

            ClanskaKartica clanKart = clanskaKarticaKor.get();

            clanKart.setKorisnik(null);
            izmenjenKorisnik.setClanskaKartica(null);

            this.korisnikRepository.save(izmenjenKorisnik);
            this.deleteById(izmenjenKorisnik.getKorisnikId());
            System.out.println("uslo u petlju za brisanje");
        }


        //drugi scenario je kad je promenjeno u "DA", da mu napravi clansku karticu ukoliko je nije imao (DAKLE promenjeno je)
        if(izmenjenKorisnik.getClanKluba().equals("DA") && izmenjenKorisnik.getClanskaKartica() == null){

            ClanskaKartica novaClanskaKart = new ClanskaKartica();



            novaClanskaKart.setBrojBodova(0);

            LocalDate currentDate = LocalDate.now();
            novaClanskaKart.setDatumIzdavanja(currentDate);

            LocalDate datumIstekaKart = currentDate.plusYears(1);
            novaClanskaKart.setDatumIsteka(datumIstekaKart);
            novaClanskaKart.setStatus("Aktivna");

            novaClanskaKart.setKorisnik(izmenjenKorisnik); //first set child reference
            izmenjenKorisnik.setClanskaKartica(novaClanskaKart); //set parent reference

            this.korisnikRepository.save(izmenjenKorisnik);

        }


    }*/


    @Override
    public List<String> getListaStatusaKartice() {
        List<String> statusiKartice = new ArrayList<>();
        statusiKartice.add("Aktivna");
        statusiKartice.add("Neaktivna");
        statusiKartice.add("Istekla");

        return statusiKartice;
    }

    @Override
    public boolean isActive(ClanskaKartica clanskaKartica) {
        return clanskaKartica.getStatus().equalsIgnoreCase("Aktivna");
    }

    @Override
    public void smanjiBrojBodovaNaKartici(ClanskaKartica clanskaKartica, Integer bodovi) {
        Integer noviBrojBodova = clanskaKartica.getBrojBodova() - bodovi;
        clanskaKartica.setBrojBodova(noviBrojBodova);
        this.update(clanskaKartica);
    }

    @Override
    public void povecajBrojBodovaNaKarticiPriPorudzbini(ClanskaKartica clanskaKartica) {
        Integer clanKartBodoviUpdated = clanskaKartica.getBrojBodova();
        clanKartBodoviUpdated += 1200;

        clanskaKartica.setBrojBodova(clanKartBodoviUpdated);
        this.update(clanskaKartica);
    }
}
