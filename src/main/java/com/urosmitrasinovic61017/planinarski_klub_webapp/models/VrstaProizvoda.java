package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.ExcludeGson;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "vrsta_proizvoda")
public class VrstaProizvoda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vrstaProizvodaId", nullable = false)
    private Integer vrstaProizvodaId;

    @NotBlank(message = "Polje naziv ne sme biti prazno")
    @Size(min = 4, max = 50, message = "Polje naziv ne sme biti duže od 50 karaktera")
    @Column(name = "naziv", nullable = false, length = 50)
    private String naziv;

    @ExcludeGson
    @OneToMany(mappedBy = "vrstaProizvoda")
    //@JsonIgnoreProperties("vrstaProizvoda")
    @JsonManagedReference
    private Set<Proizvod> listaProizvoda;



    //getters and setters
    public Integer getVrstaProizvodaId() {
        return vrstaProizvodaId;
    }

    public void setVrstaProizvodaId(Integer vrstaProizvodaId) {
        this.vrstaProizvodaId = vrstaProizvodaId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Set<Proizvod> getListaProizvoda() {
        return listaProizvoda;
    }

    public void setListaProizvoda(Set<Proizvod> listaProizvoda) {
        this.listaProizvoda = listaProizvoda;
    }


}
