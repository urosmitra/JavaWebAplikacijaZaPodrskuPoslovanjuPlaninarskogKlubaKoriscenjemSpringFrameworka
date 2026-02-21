package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Klub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlubRepository extends JpaRepository<Klub, Long> {

}
