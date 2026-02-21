package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.StavkaKorpe;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.StavkaKorpeRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ProizvodService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.StavkaKorpeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StavkaKorpeServiceImpl implements StavkaKorpeService {


    @Autowired
    private StavkaKorpeRepository stavkaKorpeRepository;

    @Autowired
    private ProizvodService proizvodService;

    @Override
    public List<StavkaKorpe> getAllStavkeKorpe() {
        return this.stavkaKorpeRepository.findAll();
    }

    @Override
    public StavkaKorpe save(StavkaKorpe stavkaKorpe) {
        return this.stavkaKorpeRepository.save(stavkaKorpe);
    }

    @Override
    public StavkaKorpe update(StavkaKorpe stavkaKorpe) {
        return this.stavkaKorpeRepository.save(stavkaKorpe);
    }

    @Override
    public void deleteById(Integer stavkaKorpeId) {
        this.stavkaKorpeRepository.deleteById(stavkaKorpeId);
    }

    @Override
    public Optional<StavkaKorpe> findById(Integer stavkaKorpeId) {
        return this.stavkaKorpeRepository.findById(stavkaKorpeId);
    }

    @Override
    public List<StavkaKorpe> getAllStavkeKorpeKorisnika(Korisnik korisnik) {
        return this.stavkaKorpeRepository.findByKorisnik(korisnik);
    }

    @Override
    public Integer izracunajUkupnuCenuStavkeKorpe(StavkaKorpe stavkaKorpe) {
        Integer izabranaKolicina = stavkaKorpe.getIzabranaKolicina();
        Integer cenaProizvoda = this.proizvodService.getCenaProizvodaSaPopustom(stavkaKorpe.getProizvod());

        return izabranaKolicina * cenaProizvoda;
    }

    @Override
    public Integer izracunajTotalCenuKorpe(List<StavkaKorpe> listaStavkiKorpe) {

        Integer ukupnaCena = 0;

        for(StavkaKorpe stavka : listaStavkiKorpe){
            Integer cenaStavke = this.izracunajUkupnuCenuStavkeKorpe(stavka);
            ukupnaCena += cenaStavke;
        }

        return ukupnaCena;
    }

    @Override
    public List<StavkaKorpe> getAllStavkeKorpeNemaNaStanju(Korisnik korisnik) {
        List<StavkaKorpe> listaStavkiKorpeNemaNaStanju = new ArrayList<>();

        List<StavkaKorpe> listaStavkiKorpeKorisnika = this.getAllStavkeKorpeKorisnika(korisnik);
        //treba da prodjemo kroz listu i uporedimo izabranu kolicinu (iz stavke) sa kolicinom u magacinu (u proizvod tabeli)

        for(StavkaKorpe stavka : listaStavkiKorpeKorisnika){
            if(stavka.getIzabranaKolicina() > stavka.getProizvod().getKolicina()){
                //ukoliko je izabrana kolicina veca od kolicine proizvoda
                //ne moze da se izvrsi porudzbina i stavljamo tu stavku u listu stavki koje su nedostupne
                listaStavkiKorpeNemaNaStanju.add(stavka);
            }

        }

        return listaStavkiKorpeNemaNaStanju;

    }

    @Override
    public StavkaKorpe findByKorisnikAndProizvod(Korisnik korisnik, Proizvod proizvod) {
        return this.stavkaKorpeRepository.findByKorisnikAndProizvod(korisnik, proizvod);
    }

    @Override
    public Integer dodajProizvodUKorpu(StavkaKorpe novaStavkaKorpe) {
        Integer dodataKolicina = novaStavkaKorpe.getIzabranaKolicina();

        Proizvod proizvod = novaStavkaKorpe.getProizvod();
        Korisnik korisnik = novaStavkaKorpe.getKorisnik();

        StavkaKorpe stavkaKorpe = this.findByKorisnikAndProizvod(korisnik, proizvod);

        if(stavkaKorpe != null){ //ako vec postoji stavka korpe korisnika sa tim proizvodom
            //proveri zbir dodate kolicine i kolicine u stavki korpe, ako je manje ili jednak 10, update-ovati stavku korpe
            Integer ukupnaKolicina = dodataKolicina + stavkaKorpe.getIzabranaKolicina();
            if(ukupnaKolicina <= 10){
                stavkaKorpe.setIzabranaKolicina(ukupnaKolicina);
            }
            else{
                //ukoliko je zbir veci od 10 treba da dodamo kolicinu do 10 proizvoda, dodata kolicina ce biti jednaka minusu 10 od stavkiKorpe!
                dodataKolicina = 10 - stavkaKorpe.getIzabranaKolicina();
                stavkaKorpe.setIzabranaKolicina(10);
            }
            this.update(stavkaKorpe);
        }
        else{
            this.save(novaStavkaKorpe);
        }

        return dodataKolicina;
    }


}
