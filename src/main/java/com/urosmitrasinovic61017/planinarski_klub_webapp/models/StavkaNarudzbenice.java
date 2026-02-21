package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "stavka_narudzbenice")
public class StavkaNarudzbenice {

//    @EmbeddedId
//    private StavkaNarudzbeniceId stavkaNarudzbeniceId;
//
//
//    @ManyToOne
//    @MapsId("narudzbenicaId")
//    @JoinColumn(name = "narudzbenicaId", referencedColumnName = "narudzbenicaId", nullable = false)
//    private Narudzbenica narudzbenica;
//
//    @ManyToOne
//    @MapsId("proizvodId")
//    @JoinColumn(name = "proizvodId", referencedColumnName = "proizvodId", nullable = false)
//    private Proizvod proizvod;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stavkaNarudzbeniceId", nullable = false)
    private Integer stavkaNarudzbeniceId;


    @NotNull(message = "Polje za izabranu količinu ne sme biti null")
    @Min(value = 1, message = "Izabrana količina ne sme biti manja od 1")
    @Max(value = 10, message = "Izabrana količina ne sme biti veća od 10")
    @Column(name = "izabranaKolicina", nullable = false)
    private Integer izabranaKolicina;

    @NotNull(message = "Ukupna cena stavke ne sme biti null")
    @PositiveOrZero(message = "Ukupna cena stavke ne sme biti negativan broj")
    @Column(name = "ukupnaCenaStavke", nullable = false)
    private Integer ukupnaCenaStavke;

    @ManyToOne
    @JoinColumn(name = "narudzbenicaId", referencedColumnName = "narudzbenicaId", nullable = false)
    @JsonIgnoreProperties("listaStavkiNarudzbenice")
    private Narudzbenica narudzbenica;

    @ManyToOne
    @JoinColumn(name = "proizvodId", referencedColumnName = "proizvodId", nullable = false)
    //@JsonIgnoreProperties("listaStavkiNarudzbeniceProizvoda")
    @JsonBackReference
    private Proizvod proizvod;


    public Integer getStavkaNarudzbeniceId() {
        return stavkaNarudzbeniceId;
    }

    public void setStavkaNarudzbeniceId(Integer stavkaNarudzbeniceId) {
        this.stavkaNarudzbeniceId = stavkaNarudzbeniceId;
    }

    public Integer getIzabranaKolicina() {
        return izabranaKolicina;
    }

    public void setIzabranaKolicina(Integer izabranaKolicina) {
        this.izabranaKolicina = izabranaKolicina;
    }

    public Integer getUkupnaCenaStavke() {
        return ukupnaCenaStavke;
    }

    public void setUkupnaCenaStavke(Integer ukupnaCenaStavke) {
        this.ukupnaCenaStavke = ukupnaCenaStavke;
    }

    public Narudzbenica getNarudzbenica() {
        return narudzbenica;
    }

    public void setNarudzbenica(Narudzbenica narudzbenica) {
        this.narudzbenica = narudzbenica;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
    }




}
