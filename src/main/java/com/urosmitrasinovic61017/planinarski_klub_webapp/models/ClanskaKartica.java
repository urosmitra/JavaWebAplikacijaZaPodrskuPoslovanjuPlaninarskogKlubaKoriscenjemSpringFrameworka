package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;


@Entity
@Table(name = "clanska_kartica")
public class ClanskaKartica {


    @Id
    @Column(name = "clanskaKarticaId", nullable = false)
    private Integer clanskaKarticaId;


    @NotNull(message = "Polje broj bodova ne sme biti null")
    @Min(value = 0, message = "Polje broj bodova ne sme biti negativan broj")
    @Column(name = "brojBodova", nullable = false)
    private Integer brojBodova;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Polje datum izdavanja ne sme biti null")
    @PastOrPresent(message = "Datum izdavanja ne sme biti budući datum")
    @Column(name = "datumIzdavanja", nullable = false)
    private LocalDate datumIzdavanja;



    //@NotNull(message = "Datum isteka ne sme biti null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Mora biti budući datum")
    @Column(name = "datumIsteka", nullable = false)
    private LocalDate datumIsteka;

    @NotBlank(message = "Status članske kartice ne sme biti prazan")
    @Size(min = 3, max = 30, message = "Status ne sme biti duži od 30 karaktera")
    @Column(name = "status", nullable = false, length = 30)
    private String status;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clanskaKarticaId", referencedColumnName = "korisnikId", nullable = false)
    @MapsId
    @JsonIgnoreProperties("clanskaKartica")
    private Korisnik korisnik;


    public Integer getClanskaKarticaId() {
        return clanskaKarticaId;
    }

    public void setClanskaKarticaId(Integer clanskaKarticaId) {
        this.clanskaKarticaId = clanskaKarticaId;
    }

    public Integer getBrojBodova() {
        return brojBodova;
    }

    public void setBrojBodova(Integer brojBodova) {
        this.brojBodova = brojBodova;
    }

    public LocalDate getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(LocalDate datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    public LocalDate getDatumIsteka() {
        return datumIsteka;
    }

    public void setDatumIsteka(LocalDate datumIsteka) {
        this.datumIsteka = datumIsteka;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
}
