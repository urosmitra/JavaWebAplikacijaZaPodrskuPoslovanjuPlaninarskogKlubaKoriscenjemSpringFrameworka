package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.VrstaProizvoda;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.VrstaProizvodaRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.VrstaProizvodaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VrstaProizvodaServiceImpl implements VrstaProizvodaService {

    @Autowired
    private VrstaProizvodaRepository vrstaProizvodaRepository;

    @Override
    public List<VrstaProizvoda> getAllVrsteProizvoda() {
        return this.vrstaProizvodaRepository.findAll();
    }

    @Override
    public VrstaProizvoda save(VrstaProizvoda vrstaProizvoda) {
        return this.vrstaProizvodaRepository.save(vrstaProizvoda);
    }

    @Override
    public VrstaProizvoda update(VrstaProizvoda vrstaProizvoda) {
        return this.vrstaProizvodaRepository.save(vrstaProizvoda);
    }

    @Override
    public void deleteById(Integer vrstaProizvodaId) {
        this.vrstaProizvodaRepository.deleteById(vrstaProizvodaId);
    }

    @Override
    public Optional<VrstaProizvoda> findById(Integer vrstaProizvodaId) {
        return this.vrstaProizvodaRepository.findById(vrstaProizvodaId);
    }
}
