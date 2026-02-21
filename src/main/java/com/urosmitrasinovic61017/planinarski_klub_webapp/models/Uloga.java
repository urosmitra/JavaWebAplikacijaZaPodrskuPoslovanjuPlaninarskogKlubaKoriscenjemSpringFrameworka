package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "uloga")
public class Uloga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ulogaId", nullable = false)
    private Integer ulogaId;


    @NotBlank(message = "Naziv ne može biti prazno polje.") //NotBlank anotaciju koristiti samo za String polja!
    //za ostala polja moze NotEmpty
    @Size(min = 3, max = 120, message = "Naziv ne može biti duži od 120 karaktera")
    @Column(name = "naziv", length = 120, nullable = false)
    private String naziv;

    @OneToMany(mappedBy = "uloga")
    @JsonIgnoreProperties("uloga")
    private Set<Korisnik> korisnici;


    public Set<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(Set<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Uloga () {

    }

    public Uloga(@NotNull(message = "Id ne može biti null.") Integer ulogaId, @NotBlank(message = "Naziv ne može biti prazno polje.") @Size(min = 3, max = 120, message = "Naziv ne može biti duži od 120 karaktera") String naziv) {
        this.ulogaId = ulogaId;
        this.naziv = naziv;
    }

    public Integer getUlogaId() {
        return ulogaId;
    }


    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }


}
