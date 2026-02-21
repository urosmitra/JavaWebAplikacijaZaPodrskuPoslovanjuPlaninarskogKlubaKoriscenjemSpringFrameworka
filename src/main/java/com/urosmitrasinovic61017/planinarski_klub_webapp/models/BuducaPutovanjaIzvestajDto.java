package com.urosmitrasinovic61017.planinarski_klub_webapp.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BuducaPutovanjaIzvestajDto {

    private Integer putovanjeId;
    private String nazivPutovanja; //***iz tabele aranzman
    private LocalDateTime datumVremePolaska;
    private LocalDate datumPovratka;
    private Integer cena; //***iz tabele aranzman
    private Integer popust;
    private Integer cenaSaPopustom;
    private Integer brojRezervacija; //brojPrijavljenih iz Putovanje entiteta
    private Long projekcijaZarade; //suma cenaPutovanja kolone iz tabele rezervacija_putovanja
    private Integer popunjenostPutovanja; //u %
    //

    //TO-DO: Zavrsiti ovu dto klasu, a onda pogledati u beleskama sta treba uraditi kako bismo obradili
    //novu kolonu cenaPutovanja iz entiteta rezervacijaPutovanja

    public BuducaPutovanjaIzvestajDto(Integer putovanjeId, String nazivPutovanja, LocalDateTime datumVremePolaska, LocalDate datumPovratka, Integer cena,
                                      Integer popust, Integer cenaSaPopustom, Integer brojRezervacija, Long projekcijaZarade, Integer popunjenostPutovanja){

        this.putovanjeId = putovanjeId;
        this.nazivPutovanja = nazivPutovanja;
        this.datumVremePolaska = datumVremePolaska;
        this.datumPovratka = datumPovratka;
        this.cena = cena;
        this.popust = popust;
        this.cenaSaPopustom = cenaSaPopustom;
        this.brojRezervacija = brojRezervacija;
        this.projekcijaZarade = projekcijaZarade;
        this.popunjenostPutovanja = popunjenostPutovanja;

    }

    public Integer getPutovanjeId() {
        return putovanjeId;
    }

    public void setPutovanjeId(Integer putovanjeId) {
        this.putovanjeId = putovanjeId;
    }

    public String getNazivPutovanja() {
        return nazivPutovanja;
    }

    public void setNazivPutovanja(String nazivPutovanja) {
        this.nazivPutovanja = nazivPutovanja;
    }

    public LocalDateTime getDatumVremePolaska() {
        return datumVremePolaska;
    }

    public void setDatumVremePolaska(LocalDateTime datumVremePolaska) {
        this.datumVremePolaska = datumVremePolaska;
    }

    public LocalDate getDatumPovratka() {
        return datumPovratka;
    }

    public void setDatumPovratka(LocalDate datumPovratka) {
        this.datumPovratka = datumPovratka;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Integer getPopust() {
        return popust;
    }

    public void setPopust(Integer popust) {
        this.popust = popust;
    }

    public Integer getCenaSaPopustom() {
        return cenaSaPopustom;
    }

    public void setCenaSaPopustom(Integer cenaSaPopustom) {
        this.cenaSaPopustom = cenaSaPopustom;
    }

    public Integer getBrojRezervacija() {
        return brojRezervacija;
    }

    public void setBrojRezervacija(Integer brojRezervacija) {
        this.brojRezervacija = brojRezervacija;
    }

    public Long getProjekcijaZarade() {
        return projekcijaZarade;
    }

    public void setProjekcijaZarade(Long projekcijaZarade) {
        this.projekcijaZarade = projekcijaZarade;
    }

    public Integer getPopunjenostPutovanja() {
        return popunjenostPutovanja;
    }

    public void setPopunjenostPutovanja(Integer popunjenostPutovanja) {
        this.popunjenostPutovanja = popunjenostPutovanja;
    }
}
