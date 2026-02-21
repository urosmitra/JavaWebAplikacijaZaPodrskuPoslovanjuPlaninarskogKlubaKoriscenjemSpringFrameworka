package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.ProveraBrojaPrijavljenih;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rezervacija_putovanja")
@ProveraBrojaPrijavljenih
public class RezervacijaPutovanja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezervacijaId", nullable = false)
    private Integer rezervacijaId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@NotNull(message = "Datum i vreme rezervacije putovanja ne sme biti null")
    @Column(name = "datumVremeRez", nullable = false)
    private LocalDateTime datumVremeRez;

    @NotNull(message = "Polje za cenu putovanje ne sme biti null")
    @Positive(message = "Cena putovanja mora biti pozitivan broj!")
    @Column(name = "cenaPutovanja", nullable = false)
    private Integer cenaPutovanja;

    @NotNull(message = "Polje status ne sme biti null")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private RezervacijaStatus status;

    @ManyToOne
    @JoinColumn(name = "korisnikId", referencedColumnName = "korisnikId", nullable = false)
    @JsonIgnoreProperties("listaRezervacija")
    private Korisnik korisnik;

    @ManyToOne
    @JoinColumn(name = "putovanjeId", referencedColumnName = "putovanjeId", nullable = false)
    @JsonIgnoreProperties("listaRezervacija")
    private Putovanje putovanje;

    public Integer getRezervacijaId() {
        return rezervacijaId;
    }

    public void setRezervacijaId(Integer rezervacijaId) {
        this.rezervacijaId = rezervacijaId;
    }

    public LocalDateTime getDatumVremeRez() {
        return datumVremeRez;
    }

    public void setDatumVremeRez(LocalDateTime datumVremeRez) {
        this.datumVremeRez = datumVremeRez;
    }

    public Integer getCenaPutovanja() {
        return cenaPutovanja;
    }

    public void setCenaPutovanja(Integer cenaPutovanja) {
        this.cenaPutovanja = cenaPutovanja;
    }

    public RezervacijaStatus getStatus() {
        return status;
    }

    public void setStatus(RezervacijaStatus status) {
        this.status = status;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Putovanje getPutovanje() {
        return putovanje;
    }

    public void setPutovanje(Putovanje putovanje) {
        this.putovanje = putovanje;
    }




}
