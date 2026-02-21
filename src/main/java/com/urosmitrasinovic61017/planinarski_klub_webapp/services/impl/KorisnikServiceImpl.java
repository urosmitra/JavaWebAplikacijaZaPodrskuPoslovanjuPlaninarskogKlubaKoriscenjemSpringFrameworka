package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.*;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.KorisnikRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ClanskaKarticaService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KorisnikService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.UlogaService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.UserAlreadyExistsException;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KorisnikServiceImpl implements KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private UlogaService ulogaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClanskaKarticaService clanskaKarticaService;

    @Override
    public List<Korisnik> getAllKorisnici() {
        return this.korisnikRepository.findAll();
    }

    @Override
    public Korisnik save(Korisnik korisnik) {
        return this.korisnikRepository.save(korisnik);
    }

    @Override
    public Korisnik update(Korisnik korisnik) {
        return this.korisnikRepository.save(korisnik);
    }

    @Override
    public void deleteById(Integer id) {
        this.korisnikRepository.deleteById(id);
    }

    @Override
    public Optional<Korisnik> findById(Integer id) {
        return this.korisnikRepository.findById(id);
    }

    @Override
    public Optional<Korisnik> findByEmail(String email) {
        return this.korisnikRepository.findByEmail(email);
    }

    @Override
    public Korisnik registracijaKorisnika(RegistracijaDTO registracijaDTO) throws UserAlreadyExistsException {
        //Check first that email doesn't already exists
        if(emailExists(registracijaDTO.getEmail())){
            throw  new UserAlreadyExistsException("Nalog sa unetom email adresom već postoji!");
        }

        //Mapiranje DTO objekta sa entitijem Korisnik
        Korisnik noviKorisnik = new Korisnik();
        noviKorisnik.setIme(registracijaDTO.getIme());
        noviKorisnik.setPrezime(registracijaDTO.getPrezime());
        noviKorisnik.setEmail(registracijaDTO.getEmail());
        noviKorisnik.setPassword(passwordEncoder.encode(registracijaDTO.getPassword()));
        noviKorisnik.setBrojTelefona(registracijaDTO.getBrojTelefona());
        Uloga ulogaKorisnika = this.ulogaService.findByNaziv("KORISNIK").get();
        noviKorisnik.setUloga(ulogaKorisnika);


        //sacuvaj u bazi
        this.korisnikRepository.save(noviKorisnik);

        ClanskaKartica novaClanskaKartica = new ClanskaKartica();
        novaClanskaKartica.setKorisnik(noviKorisnik);

        LocalDate datumIzdavanja = LocalDate.now();
        novaClanskaKartica.setDatumIzdavanja(datumIzdavanja);

        LocalDate datumIsteka = datumIzdavanja.plusYears(1);
        novaClanskaKartica.setDatumIsteka(datumIsteka);
        novaClanskaKartica.setStatus("Aktivna");
        novaClanskaKartica.setBrojBodova(0);

        //sacuvaj novu clansku karticu korisnika u bazi
        this.clanskaKarticaService.save(novaClanskaKartica);

        return noviKorisnik;

    }

    @Override
    public List<Korisnik> findAllByKeyword(String keyword) {
        return this.korisnikRepository.findByKeyword(keyword);
    }

    @Override
    public void izmenaKorisnickogNaloga(Korisnik korisnik) throws UserAlreadyExistsException {
        if(newEmailAlreadyExists(korisnik)){
            throw new UserAlreadyExistsException("Nova email adresa koju ste uneli je zauzeta!");
        }

        this.update(korisnik);

    }

    @Override
    public void changeKorisnikPassword(Korisnik korisnik, KorisnikChangePassDto korisnikChangePassDto) {

        korisnik.setPassword(this.passwordEncoder.encode(korisnikChangePassDto.getNewPassword()));
        update(korisnik);

    }

    private boolean newEmailAlreadyExists(Korisnik korisnik){
        //treba da izbacimo email koji je vec bio, odnosno korisnika sa tim email-om iz findAll korisnika
        //i da onda uporedimo izmenjen korisnikov email sa ostalim korisnicima
        List<Korisnik> listaKorisnika = this.getAllKorisnici();

        List<Korisnik> listaKorisnikaBezThisKorisnika = listaKorisnika.stream().
                filter(k -> k.getKorisnikId() != korisnik.getKorisnikId()).collect(Collectors.toList());

       for(Korisnik k : listaKorisnikaBezThisKorisnika){
           if(k.getEmail().equals(korisnik.getEmail())){
               return true;
           }
       }

       return false;

    }


    private boolean emailExists(String email){
        Optional<Korisnik> korisnikOpt = this.korisnikRepository.findByEmail(email);

        return korisnikOpt.isPresent();
    }

}
