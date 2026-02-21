package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Narudzbenica;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.StavkaKorpe;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.NarudzbenicaRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.NarudzbenicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NarudzbenicaServiceImpl implements NarudzbenicaService {


    @Autowired
    private NarudzbenicaRepository narudzbenicaRepository;

    @Override
    public List<Narudzbenica> getAllNarudzbenice() {
        return this.narudzbenicaRepository.findAll();
    }

    @Override
    public Narudzbenica save(Narudzbenica narudzbenica) {
        return this.narudzbenicaRepository.save(narudzbenica);
    }

    @Override
    public Narudzbenica update(Narudzbenica narudzbenica) {
        return this.narudzbenicaRepository.save(narudzbenica);
    }

    @Override
    public void deleteById(Integer narudzbenicaId) {
        this.narudzbenicaRepository.deleteById(narudzbenicaId);
    }

    @Override
    public Optional<Narudzbenica> findById(Integer narudzbenicaId) {
        return this.narudzbenicaRepository.findById(narudzbenicaId);
    }

    @Override
    public List<Narudzbenica> getAllNarudzbeniceKorisnika(Korisnik korisnik) {
        return this.narudzbenicaRepository.findByKorisnik(korisnik);
    }

    @Override
    public List<Narudzbenica> getAllNarudzbeniceKorisnikaUPoslednjihMesecDana(Integer korisnikId) {
        return this.narudzbenicaRepository.getAllNarudzbeniceKorisnikaUPoslednjihMesecDana(korisnikId);
    }

    @Override
    public List<Narudzbenica> getAllNarudzbeniceKorisnikaStarijihOdMesecDana(Integer korisnikId) {
        return this.narudzbenicaRepository.getAllNarudzbeniceKorisnikaStarijihOdMesecDana(korisnikId);
    }

}
