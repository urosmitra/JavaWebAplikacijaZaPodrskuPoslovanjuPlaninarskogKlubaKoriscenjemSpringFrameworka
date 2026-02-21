package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "stavka_korpe")
public class StavkaKorpe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stavkaKorpeId", nullable = false)
    private Integer stavkaKorpeId;

    @NotNull(message = "Polje za izabranu količinu ne sme biti null")
    @Min(value = 1, message = "Izabrana količina mora biti bar 1")
    @Max(value = 10, message = "Izabrana količina ne sme biti veća od 10")
    @Column(name = "izabranaKolicina", nullable = false)
    private Integer izabranaKolicina;


    @ManyToOne
    @JoinColumn(name = "proizvodId", referencedColumnName = "proizvodId", nullable = false)
    //@JsonIgnoreProperties("listaStavkiKorpeProizvoda")
    @JsonBackReference
    private Proizvod proizvod;


    @ManyToOne
    @JoinColumn(name = "korisnikId", referencedColumnName = "korisnikId", nullable = false)
    @JsonBackReference
    private Korisnik korisnik;



    //getters and setters
    public Integer getStavkaKorpeId() {
        return stavkaKorpeId;
    }

    public void setStavkaKorpeId(Integer stavkaKorpeId) {
        this.stavkaKorpeId = stavkaKorpeId;
    }

    public Integer getIzabranaKolicina() {
        return izabranaKolicina;
    }

    public void setIzabranaKolicina(Integer izabranaKolicina) {
        this.izabranaKolicina = izabranaKolicina;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
}
