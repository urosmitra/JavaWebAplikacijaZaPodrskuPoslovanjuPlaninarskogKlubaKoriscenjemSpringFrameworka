package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.MyEmailValidation;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.UniqueEmailCheck;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "korisnik")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "korisnikId", nullable = false)
    private Integer korisnikId;

    @NotBlank(message = "Ime ne može biti prazno polje.")
    @Size(min = 2, max = 50, message = "Ime ne sme biti manje od 2 karaktera i duže od 50 karaktera.")
    @Column(name = "ime", nullable = false, length = 80)
    @Pattern(regexp = "^[A-Z][a-z]{1,30}(\\s[A-Z][a-z]{1,30})?$", message = "Ime ne sme da sadrži brojeve i mora biti pravilno napisano (veliko slovo, minimum 2 karaktera itd.)")
    private String ime;

    @NotBlank(message = "Prezime ne može biti prazno polje.")
    @Size(min = 2, max = 100, message = "Prezime ne može biti duže od 100 karaktera.")
    @Pattern(regexp = "^[A-Z][a-z]{1,40}(\\s[A-Z][a-z]{1,30})?$", message = "Prezime ne sme da sadrži brojeve i mora biti pravilno napisano (veliko slovi itd.). Ne podržava specijalna slova srpske latinice.")
    @Column(name = "prezime", nullable = false, length = 100)
    private String prezime;

    @NotBlank(message = "Email ne može biti prazno polje.")
    @Size(max = 110, message = "Email ne može biti duži od 110 karaktera.")
    @MyEmailValidation
    //@UniqueEmailCheck(message = "Email koji ste uneli je već zauzet")
    @Column(name = "email", nullable = false, length = 110, unique = true)
    private String email;


    @NotBlank(message = "Password ne može biti prazno polje")
    //@Size(min = 5, max = 25, message = "Password mora sadržati izmedju 5 i 25 karaktera")
    //@Pattern(regexp = "^[\\w@-]{5,25}$", message = "Password može sadržati velika, mala slova, brojeve i znakove: '_', '@' i '-'")
    @Column(name = "password", nullable = false, length = 80)
    private String password;

    @NotBlank(message = "Broj telefona ne može biti prazno polje")
    @Pattern(regexp = "^((0)|(\\+381))6\\d(([0-9]{7})|([0-9]{6}))$", message = "Morate uneti validan srpski broj telefona")
    @Column(name = "brojTelefona", nullable = false, length = 60)
    private String brojTelefona;


    @ManyToOne
    @JoinColumn(name = "ulogaId", referencedColumnName = "ulogaId", nullable = false)
    @JsonIgnoreProperties("korisnici")
    private Uloga uloga;

    @OneToOne(mappedBy = "korisnik", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnoreProperties("korisnik")
    private ClanskaKartica clanskaKartica;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("korisnik")
    private List<RezervacijaPutovanja> listaRezervacija;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("korisnik")
    private List<Narudzbenica> listaNarudzbenica;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<StavkaKorpe> listaStavkiKorpeKorisnika;



    public Korisnik() {

    }


    //geteri i seteri

    public Integer getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Integer korisnikId){
        this.korisnikId = korisnikId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }


    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }


    public ClanskaKartica getClanskaKartica() {
        return clanskaKartica;
    }

    public void setClanskaKartica(ClanskaKartica clanskaKartica) {
        this.clanskaKartica = clanskaKartica;
    }


    public List<RezervacijaPutovanja> getListaRezervacija() {
        return listaRezervacija;
    }

    public void setListaRezervacija(List<RezervacijaPutovanja> listaRezervacija) {
        this.listaRezervacija = listaRezervacija;
    }

    public List<Narudzbenica> getListaNarudzbenica() {
        return listaNarudzbenica;
    }

    public void setListaNarudzbenica(List<Narudzbenica> listaNarudzbenica) {
        this.listaNarudzbenica = listaNarudzbenica;
    }

    public List<StavkaKorpe> getListaStavkiKorpeKorisnika() {
        return listaStavkiKorpeKorisnika;
    }

    public void setListaStavkiKorpeKorisnika(List<StavkaKorpe> listaStavkiKorpeKorisnika) {
        this.listaStavkiKorpeKorisnika = listaStavkiKorpeKorisnika;
    }



}
