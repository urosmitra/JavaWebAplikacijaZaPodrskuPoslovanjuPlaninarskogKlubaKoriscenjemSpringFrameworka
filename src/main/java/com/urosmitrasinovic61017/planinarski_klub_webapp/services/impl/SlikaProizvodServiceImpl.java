package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.SlikaProizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.SlikaProizvodRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.SlikaProizvodService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.NazivSlikeProizvodaAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SlikaProizvodServiceImpl implements SlikaProizvodService {


    @Autowired
    private SlikaProizvodRepository slikaProizvodRepo;

    @Override
    public List<SlikaProizvod> getAllSlikeSvihProizvoda() {
        return this.slikaProizvodRepo.findAll();
    }

    @Override
    public SlikaProizvod save(SlikaProizvod slikaProizvod) throws NazivSlikeProizvodaAlreadyExists {

        Proizvod proizvod = slikaProizvod.getProizvod();

        //proveriti da li postoji slika sa istim nazivom u okviru slika proizvoda za dati proizvod, ako postoji ne sejvujemo!
        List<SlikaProizvod> listaSlika = this.getAllSlikeProizvoda(proizvod);

        for(SlikaProizvod sl : listaSlika){
            if(sl.getNazivSlike().equals(slikaProizvod.getNazivSlike())){
                throw new NazivSlikeProizvodaAlreadyExists("Izabrana slika već postoji u okviru slika ovog proizvoda pod tim nazivom! (Naziv fajla: " + sl.getNazivSlike() + ")");
            }
        }

        return this.slikaProizvodRepo.save(slikaProizvod);
    }

    @Override
    public SlikaProizvod update(SlikaProizvod slikaProizvod) {
        return this.slikaProizvodRepo.save(slikaProizvod);
    }

    @Override
    public void deleteById(Integer slikaProizvodId) {
        this.slikaProizvodRepo.deleteById(slikaProizvodId);
    }

    @Override
    public Optional<SlikaProizvod> findById(Integer slikaProizvodId) {
        return this.slikaProizvodRepo.findById(slikaProizvodId);
    }

    @Override
    public List<SlikaProizvod> getAllSlikeProizvoda(Proizvod proizvod) {
        return this.slikaProizvodRepo.findByProizvod(proizvod);
    }
}
