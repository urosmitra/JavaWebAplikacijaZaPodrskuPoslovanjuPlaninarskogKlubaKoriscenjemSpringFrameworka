package com.urosmitrasinovic61017.planinarski_klub_webapp.config;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {


    //sada kreiramo polja koje ce biti odgovarajuca poljima iz Korisnik modela
    private Integer korisnikId;
    private String ime;
    private String prezime;
    private String email;
    private String password;
    private String brojTelefona;
    private List<GrantedAuthority> authorities;


    public MyUserDetails(Korisnik korisnik){
        //ovde mapiramo polja od MyUserDetails sa poljima od dobijenog korisnika iz repozitorijuma (baze)
        this.korisnikId = korisnik.getKorisnikId();
        this.ime = korisnik.getIme();
        this.prezime = korisnik.getPrezime();
        this.email = korisnik.getEmail();
        this.password = korisnik.getPassword();
        this.brojTelefona = korisnik.getBrojTelefona();

        this.authorities = Arrays.stream(korisnik.getUloga().getNaziv().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
    }

    public MyUserDetails() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //dodatni geteri za preostala polja


    public Integer getKorisnikId() {
        return korisnikId;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

}
