package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "narudzbenica")
public class Narudzbenica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "narudzbenicaId", nullable = false)
    private Integer narudzbenicaId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@NotNull(message = "Polje za datum i vreme narudžbenice ne sme biti null")
    @Column(name = "datumVreme", nullable = false)
    private LocalDateTime datumVreme;


    @NotBlank(message = "Adresa za isporuku ne sme biti prazno polje")
    @Size(min = 3, max = 100, message = "Adresa za isporuku ne sme kraća od 3 niti duža od 100 karaktera")
    @Pattern(regexp = "^[A-Z][a-z]{2,}(\\s[A-Z][a-z]{2,}){0,}\\s\\d{1,3}([a-zA-Z])?$", message = "Unesite validnu adresu za isporuku (morate uključiti i broj ulice)")
    @Column(name = "adresa_za_isporuku", nullable = false, length = 120)
    private String adresaZaIsporuku;

    @NotBlank(message = "Grad ne sme biti prazno polje")
    @Size(min = 2, max = 60, message = "Grad ne sme biti kraći od 2 karkatera duži od 60 karaktera")
    @Pattern(regexp = "^[A-Z][a-z]+(\\s[A-Z][a-z]+)?$", message = "Unesite validan naziv grada (veliko slovo, ne sme sadržati brojeve)")
    @Column(name = "grad", nullable = false, length = 60)
    private String grad;

    @NotBlank(message = "Poštanski broj ne sme biti prazan")
    @Pattern(regexp = "^\\d{5}$", message = "Srpski poštanski broj mora sadržati 5 brojeva")
    @Column(name = "zip_code", nullable = false, length = 20)
    private String zipCode;

    @NotNull(message = "Ukupna cena ne sme biti null polje")
    @Positive(message = "Ukupna cena mora biti pozitivan broj")
    @Column(name = "totalCena", nullable = false)
    private Integer totalCena;


    @ManyToOne
    @JoinColumn(name = "korisnikId", referencedColumnName = "korisnikId", nullable = false)
    @JsonIgnoreProperties("listaNarudzbenica")
    private Korisnik korisnik;

    @OneToMany(mappedBy = "narudzbenica", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("narudzbenica")
    private List<StavkaNarudzbenice> listaStavkiNarudzbenice;



    public Integer getNarudzbenicaId() {
        return narudzbenicaId;
    }

    public void setNarudzbenicaId(Integer narudzbenicaId) {
        this.narudzbenicaId = narudzbenicaId;
    }

    public LocalDateTime getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(LocalDateTime datumVreme) {
        this.datumVreme = datumVreme;
    }

    public String getAdresaZaIsporuku() {
        return adresaZaIsporuku;
    }

    public void setAdresaZaIsporuku(String adresaZaIsporuku) {
        this.adresaZaIsporuku = adresaZaIsporuku;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getTotalCena() {
        return totalCena;
    }

    public void setTotalCena(Integer totalCena) {
        this.totalCena = totalCena;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public List<StavkaNarudzbenice> getListaStavkiNarudzbenice() {
        return listaStavkiNarudzbenice;
    }

    public void setListaStavkiNarudzbenice(List<StavkaNarudzbenice> listaStavkiNarudzbenice) {
        this.listaStavkiNarudzbenice = listaStavkiNarudzbenice;
    }



}
