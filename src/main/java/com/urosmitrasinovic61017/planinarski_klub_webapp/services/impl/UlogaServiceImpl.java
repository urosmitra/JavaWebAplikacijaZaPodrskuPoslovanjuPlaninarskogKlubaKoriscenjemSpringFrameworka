package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Uloga;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.UlogaRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.UlogaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UlogaServiceImpl implements UlogaService {


    @Autowired
    private UlogaRepository ulogaRepository;

    @Override
    public List<Uloga> getAllUloge() {
        return this.ulogaRepository.findAll();
    }

    @Override
    public Uloga save(Uloga uloga) {
        return this.ulogaRepository.save(uloga);
    }

    @Override
    public Uloga update(Uloga uloga) {
        return this.ulogaRepository.save(uloga);
    }

    @Override
    public void deleteById(Integer id) {
        this.ulogaRepository.deleteById(id);
    }

    @Override
    public Optional<Uloga> findById(Integer id) {
        return this.ulogaRepository.findById(id);
    }

    @Override
    public Optional<Uloga> findByNaziv(String naziv) {
        return this.ulogaRepository.findByNaziv(naziv);
    }


}
