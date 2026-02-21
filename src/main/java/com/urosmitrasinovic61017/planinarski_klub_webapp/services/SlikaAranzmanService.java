package com.urosmitrasinovic61017.planinarski_klub_webapp.services;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.SlikaAranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.NazivSlikeAranzmanaAlreadyExists;

import java.util.List;
import java.util.Optional;

public interface SlikaAranzmanService {

    List<SlikaAranzman> getAllSlikeSvihAranzmana();

    SlikaAranzman save(SlikaAranzman slikaAranzman) throws NazivSlikeAranzmanaAlreadyExists;

    SlikaAranzman update(SlikaAranzman slikaAranzman);

    void deleteById(Integer id);

    Optional<SlikaAranzman> findById(Integer id);

    List<SlikaAranzman> getAllSlikeAranzmana(Integer aranzmanId);




}
