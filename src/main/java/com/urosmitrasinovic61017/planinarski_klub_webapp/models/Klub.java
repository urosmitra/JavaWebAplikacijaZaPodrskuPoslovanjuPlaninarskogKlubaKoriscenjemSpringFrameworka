package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.MyEmailValidation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "klub")
public class Klub {

    @Id
    @Column(name = "PIB", nullable = false)
    private Long PIB;

    @NotBlank(message = "Broj telefona ne može biti prazno polje")
    @Pattern(regexp = "^(\\+3816\\d(([0-9]{7})|([0-9]{6})))|(\\+38111[0-9]{8})$", message = "Morate uneti validan srpski broj telefona")
    @Column(name = "brojTelefona", nullable = false, length = 100)
    private String brojTelefona;

    @NotBlank(message = "Email ne može biti prazno polje.")
    @Size(max = 100, message = "Email ne može biti duži od 110 karaktera.")
    @MyEmailValidation
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @NotBlank(message = "Adresa kluba ne sme biti prazno polje.")
    @Size(max = 100, message = "Adresa ne sme biti duža od 100 karaktera.")
    @Column(name = "adresa", nullable = false, length = 100)
    private String adresa;

    @Column(name = "opis", columnDefinition = "TEXT")
    private String opis;



    public Long getPIB() {
        return PIB;
    }

    public void setPIB(Long PIB) {
        this.PIB = PIB;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
