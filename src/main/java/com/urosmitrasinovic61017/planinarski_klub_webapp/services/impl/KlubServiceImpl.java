package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Klub;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.KlubRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KlubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KlubServiceImpl implements KlubService {


    @Autowired
    private KlubRepository klubRepository;

    @Override
    public List<Klub> getAllKlubovi() {
        return this.klubRepository.findAll();
    }

    @Override
    public Klub save(Klub klub) {
        return this.klubRepository.save(klub);
    }

    @Override
    public Klub update(Klub klub) {
        return this.klubRepository.save(klub);
    }

    @Override
    public void deleteById(Long idKluba) {
        this.klubRepository.deleteById(idKluba);
    }

    @Override
    public Optional<Klub> findById(Long idKluba) {
        return this.klubRepository.findById(idKluba);
    }
}
