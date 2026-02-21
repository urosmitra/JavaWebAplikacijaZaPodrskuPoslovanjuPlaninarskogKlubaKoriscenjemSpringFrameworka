package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Aranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.SlikaAranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.SlikaAranzmanRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.AranzmanService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.SlikaAranzmanService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.NazivSlikeAranzmanaAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SlikaAranzmanServiceImpl implements SlikaAranzmanService {

    @Autowired
    private SlikaAranzmanRepository slikaAranzmanRep;

    @Autowired
    private AranzmanService aranzmanService;

    @Override
    public List<SlikaAranzman> getAllSlikeSvihAranzmana() {
        return this.slikaAranzmanRep.findAll();
    }

    @Override
    public SlikaAranzman save(SlikaAranzman slikaAranzman) throws NazivSlikeAranzmanaAlreadyExists {

        Aranzman aranzman = slikaAranzman.getAranzman();

        //proveriti da li postoji slika sa istim nazivom u okviru slika aranzmana za dati aranzman, ako postoji ne sejvujemo!
        List<SlikaAranzman> listaSlika = getAllSlikeAranzmana(aranzman.getAranzmanId());

        for (SlikaAranzman sa : listaSlika) {
            if (sa.getNazivSlike().equals(slikaAranzman.getNazivSlike())) {
                throw new NazivSlikeAranzmanaAlreadyExists("Izabrana slika već postoji u okviru galerije slika ovog Aranžmana (Naziv fajla: " + slikaAranzman.getNazivSlike() + ")");
            }
        }

        return this.slikaAranzmanRep.save(slikaAranzman);
    }

    @Override
    public SlikaAranzman update(SlikaAranzman slikaAranzman) {
        return this.slikaAranzmanRep.save(slikaAranzman);
    }

    @Override
    public void deleteById(Integer id) {
        this.slikaAranzmanRep.deleteById(id);
    }

    @Override
    public Optional<SlikaAranzman> findById(Integer id) {
        return this.slikaAranzmanRep.findById(id);
    }

    @Override
    public List<SlikaAranzman> getAllSlikeAranzmana(Integer aranzmanId) {
        return this.slikaAranzmanRep.findByAranzmanAranzmanId(aranzmanId);
    }

}
