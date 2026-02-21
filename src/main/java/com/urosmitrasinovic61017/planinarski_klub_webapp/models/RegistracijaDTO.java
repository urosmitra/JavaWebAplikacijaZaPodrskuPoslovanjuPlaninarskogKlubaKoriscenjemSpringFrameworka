package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.MyEmailValidation;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.PasswordMatches;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//registracija korisnika data transfer object!
@PasswordMatches
public class RegistracijaDTO {

    @NotBlank(message = "Ime ne može biti prazno polje.")
    @Size(min = 2, max = 50, message = "Ime ne sme biti manje od 2 karaktera i duže od 50 karaktera.")
    @Pattern(regexp = "^[A-Z][a-z]{1,30}(\\s[A-Z][a-z]{1,30})?$", message = "Ime ne sme da sadrži brojeve i mora biti pravilno napisano (veliko slovo itd.)")
    private String ime;

    @NotBlank(message = "Prezime ne može biti prazno polje.")
    @Size(min = 2, max = 100, message = "Prezime ne može biti duže od 80 karaktera.")
    @Pattern(regexp = "^[A-Z][a-z]{1,40}(\\s[A-Z][a-z]{1,30})?$", message = "Prezime ne sme da sadrži brojeve i mora biti pravilno napisano (veliko slovo itd.)")
    private String prezime;

    @NotBlank(message = "Email ne može biti prazno polje.")
    @Size(max = 110, message = "Email ne može biti duži od 110 karaktera.")
    @MyEmailValidation
    private String email;

    @NotBlank(message = "Password ne može biti prazno polje")
    @Size(min = 5, max = 25, message = "Password mora sadržati izmedju 5 i 25 karaktera")
    @Pattern(regexp = "^[\\w@-]{5,25}$", message = "Password može sadržati velika, mala slova, brojeve i znakove: '_', '@' i '-'")
    private String password;

    @NotBlank(message = "Password ne može biti prazno polje")
    @Size(min = 5, max = 25, message = "Password mora sadržati izmedju 5 i 25 karaktera")
    @Pattern(regexp = "^[\\w@-]{5,25}$", message = "Password može sadržati velika, mala slova, brojeve i znakove: '_', '@' i '-'")
    private String matchingPassword;


    @NotBlank(message = "Broj telefona ne može biti prazno polje")
    @Pattern(regexp = "^((0)|(\\+381))6\\d(([0-9]{7})|([0-9]{6}))$", message = "Morate uneti validan srpski broj telefona")
    private String brojTelefona;


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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }
}
