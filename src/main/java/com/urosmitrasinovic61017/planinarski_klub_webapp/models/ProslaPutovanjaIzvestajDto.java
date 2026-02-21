package com.urosmitrasinovic61017.planinarski_klub_webapp.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProslaPutovanjaIzvestajDto {

    private Integer putovanjeId;
    private String nazivPutovanja; //***iz tabele aranzman
    private LocalDateTime datumVremePolaska;
    private LocalDate datumPovratka;
    private Integer brojPutnika; ////brojPrijavljenih iz Putovanje entiteta
    private PutovanjeStatus status;
    private Double prosecnaZaradaPoPutniku; //avg cenaPutovanja kolone iz tabele rezervacija_putovanja
    //zato sto zavisno od tadasnje cene aranzmana i tadasnjeg popusta putovanja u trenutku rezervacije
    //putnici su mogli da plate drugaciju sumu novca
    //Originalna cena aranzmana u datom periodu bi mogla da se stavi kao kolona u Putovanje entitetu
    //ali to bi puno dodatnih promena zahtevalo u projektu, tako da ovog puta necemo to dodavati
    private Long ukupnaZarada; ///suma cenaPutovanja kolone iz tabele rezervacija_putovanja
    private Integer popunjenostPutovanja; //u %
    private String izvestaj;

    public ProslaPutovanjaIzvestajDto(Integer putovanjeId, String nazivPutovanja, LocalDateTime datumVremePolaska,
                                      LocalDate datumPovratka, Integer brojPutnika, PutovanjeStatus status, Double prosecnaZaradaPoPutniku,
                                      Long ukupnaZarada, Integer popunjenostPutovanja, String izvestaj){

        this.putovanjeId = putovanjeId;
        this.nazivPutovanja = nazivPutovanja;
        this.datumVremePolaska = datumVremePolaska;
        this.datumPovratka = datumPovratka;
        this.brojPutnika = brojPutnika;
        this.status = status;
        this.prosecnaZaradaPoPutniku = prosecnaZaradaPoPutniku;
        this.ukupnaZarada = ukupnaZarada;
        this.popunjenostPutovanja = popunjenostPutovanja;
        this.izvestaj = izvestaj;

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

    public Integer getBrojPutnika() {
        return brojPutnika;
    }

    public void setBrojPutnika(Integer brojPutnika) {
        this.brojPutnika = brojPutnika;
    }

    public PutovanjeStatus getStatus() {
        return status;
    }

    public void setStatus(PutovanjeStatus status) {
        this.status = status;
    }

    public Double getProsecnaZaradaPoPutniku() {
        return prosecnaZaradaPoPutniku;
    }

    public void setProsecnaZaradaPoPutniku(Double prosecnaZaradaPoPutniku) {
        this.prosecnaZaradaPoPutniku = prosecnaZaradaPoPutniku;
    }

    public Long getUkupnaZarada() {
        return ukupnaZarada;
    }

    public void setUkupnaZarada(Long ukupnaZarada) {
        this.ukupnaZarada = ukupnaZarada;
    }

    public Integer getPopunjenostPutovanja() {
        return popunjenostPutovanja;
    }

    public void setPopunjenostPutovanja(Integer popunjenostPutovanja) {
        this.popunjenostPutovanja = popunjenostPutovanja;
    }

    public String getIzvestaj() {
        return izvestaj;
    }

    public void setIzvestaj(String izvestaj) {
        this.izvestaj = izvestaj;
    }
}
