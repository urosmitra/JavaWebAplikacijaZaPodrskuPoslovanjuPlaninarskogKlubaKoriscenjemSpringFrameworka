package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.RezervacijaPutovanja;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.RezervacijaStatus;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.RezervacijaPutovanjaRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.RezervacijaPutovanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RezervacijaPutovanjeServiceImpl implements RezervacijaPutovanjeService {

    @Autowired
    private RezervacijaPutovanjaRepository rezervacijaRepository;


    @Override
    public List<RezervacijaPutovanja> getAllRezervacije() {
        return this.rezervacijaRepository.findAll();
    }

    @Override
    public RezervacijaPutovanja save(RezervacijaPutovanja rezervacija) {
        return this.rezervacijaRepository.save(rezervacija);
    }

    @Override
    public RezervacijaPutovanja update(RezervacijaPutovanja rezervacija) {
        return this.rezervacijaRepository.save(rezervacija);
    }

    @Override
    public void deleteById(Integer id) {
        this.rezervacijaRepository.deleteById(id);
    }

    @Override
    public Optional<RezervacijaPutovanja> findById(Integer id) {
        return this.rezervacijaRepository.findById(id);
    }

    @Override
    public List<RezervacijaPutovanja> getAllRezervacijePutovanja(Integer putovanjeId) {
        return this.rezervacijaRepository.findByPutovanjePutovanjeId(putovanjeId);
    }

    @Override
    public void setStatusRezervacijaPutovanjeOtkazano(List<RezervacijaPutovanja> listaRezervacija){
        for(RezervacijaPutovanja rez : listaRezervacija){
            if(!rez.getStatus().equals(RezervacijaStatus.PUTOVANJE_OTKAZANO)){ //ukoliko status NIJE jednak PUTOVANJE_OTKAZANO
                this.rezervacijaRepository.updateStatusPutovanjeOtkazano(rez.getRezervacijaId());
            }
        }
    }

    @Override
    public void setStatusRezervacijaPutovanjeOdrzano(List<RezervacijaPutovanja> listaRezervacija) {
        for(RezervacijaPutovanja rez : listaRezervacija){
            if(!rez.getStatus().equals(RezervacijaStatus.PUTOVANJE_ODRZANO)){
                this.rezervacijaRepository.updateStatusPutovanjeOdrzano(rez.getRezervacijaId());
            }
        }
    }

    @Override
    public boolean daLiKorisnikImaRezervaciju(List<RezervacijaPutovanja> listaRezervacija, Korisnik korisnik) {

        for(RezervacijaPutovanja rez : listaRezervacija){
            if(rez.getKorisnik().getKorisnikId().equals(korisnik.getKorisnikId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<RezervacijaPutovanja> getAllRezervacijePutovanjaKorisnikaAktivne(Integer korisnikId) {
        return this.rezervacijaRepository.getAllRezervacijePutovanjaKorisnikaAktivne(korisnikId);
    }

    @Override
    public List<RezervacijaPutovanja> getAllRezervacijaPutovanjaKorisnikaIstorija(Integer korisnikId) {
        return this.rezervacijaRepository.getAllRezervacijaPutovanjaKorisnikaIstorija(korisnikId);
    }

    @Override
    public Integer izracunajCenuPutovanja(Integer popustPutovanja, Integer cenaAranzmana) {

        if(popustPutovanja != 0){
           cenaAranzmana = cenaAranzmana - cenaAranzmana * popustPutovanja/100;
        }
        return cenaAranzmana;
    }


}
