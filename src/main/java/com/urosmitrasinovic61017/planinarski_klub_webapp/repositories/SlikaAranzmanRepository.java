package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.SlikaAranzman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlikaAranzmanRepository extends JpaRepository<SlikaAranzman, Integer> {

    //pronadji sve slike za dati aranzman
    List<SlikaAranzman> findByAranzmanAranzmanId(Integer aranzmanId);

}
