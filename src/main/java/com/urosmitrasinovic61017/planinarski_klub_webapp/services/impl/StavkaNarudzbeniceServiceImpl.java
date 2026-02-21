package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Narudzbenica;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.StavkaKorpe;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.StavkaNarudzbenice;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.NarudzbenicaRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.StavkaNarudzbeniceRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ProizvodService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.StavkaKorpeService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.StavkaNarudzbeniceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StavkaNarudzbeniceServiceImpl implements StavkaNarudzbeniceService {

    @Autowired
    private StavkaNarudzbeniceRepository stavkaNarudzbeniceRepo;

    @Autowired
    private NarudzbenicaRepository narudzbenicaRepository;

    @Autowired
    private StavkaKorpeService stavkaKorpeService;

    @Autowired
    private ProizvodService proizvodService;


    @Override
    public List<StavkaNarudzbenice> getAllStavkeNarudzbenice() {
        return this.stavkaNarudzbeniceRepo.findAll();
    }

    @Override
    public StavkaNarudzbenice save(StavkaNarudzbenice stavkaNar) {
        return this.stavkaNarudzbeniceRepo.save(stavkaNar);
    }

    @Override
    public StavkaNarudzbenice update(StavkaNarudzbenice stavkaNar) {
        return this.stavkaNarudzbeniceRepo.save(stavkaNar);
    }

    @Override
    public void deleteById(Integer stavkaNarudzbeniceId) {
        this.stavkaNarudzbeniceRepo.deleteById(stavkaNarudzbeniceId);
    }

    @Override
    public Optional<StavkaNarudzbenice> findById(Integer stavkaNarId) {
        return this.stavkaNarudzbeniceRepo.findById(stavkaNarId);
    }

    @Override
    public List<StavkaNarudzbenice> findAllByNarudzbenica(Narudzbenica narudzbenica) {
        return this.stavkaNarudzbeniceRepo.findByNarudzbenica(narudzbenica);
    }

    @Override
    public List<StavkaNarudzbenice> findAllByProizvod(Proizvod proizvod) {
        return this.stavkaNarudzbeniceRepo.findByProizvod(proizvod);
    }

    @Override
    public void saveStavkeNarudzbeniceAndUpdateKolicinuProizvoda(Integer narudzbenicaId, List<StavkaKorpe> listaStavkiKorpe) {
        //treba da mapiramo stavku korpe sa stavkama narudzbenice
        //update-ujemo kolicinu proizvoda u magacinu
        //sacuvamo stavke narudzbenice u bazu
        //obrisemo stavke kopre iz baze
        Narudzbenica narudzbenica = this.narudzbenicaRepository.findById(narudzbenicaId).get();

        for(StavkaKorpe stavkaKorpe : listaStavkiKorpe){
            StavkaNarudzbenice novaStavkaNar = new StavkaNarudzbenice();
            novaStavkaNar.setNarudzbenica(narudzbenica);

            novaStavkaNar.setIzabranaKolicina(stavkaKorpe.getIzabranaKolicina());

            Integer ukupnaCenaStavke = this.stavkaKorpeService.izracunajUkupnuCenuStavkeKorpe(stavkaKorpe);
            novaStavkaNar.setUkupnaCenaStavke(ukupnaCenaStavke);

            novaStavkaNar.setProizvod(stavkaKorpe.getProizvod());
            this.stavkaNarudzbeniceRepo.save(novaStavkaNar);

            //update kolicina u proizvodu
            this.proizvodService.smanjiKolicinuUMagacinu(novaStavkaNar.getProizvod(), novaStavkaNar.getIzabranaKolicina());

            //brisanje stavki korpe iz baze
            this.stavkaKorpeService.deleteById(stavkaKorpe.getStavkaKorpeId());

        }

    }


}
