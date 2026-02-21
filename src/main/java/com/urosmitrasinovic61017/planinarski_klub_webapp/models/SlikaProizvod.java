package com.urosmitrasinovic61017.planinarski_klub_webapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "slika_proizvod")
public class SlikaProizvod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slikaProizvodId", nullable = false)
    private Integer slikaProizvodId;

    @NotBlank(message = "Naziv slike ne sme biti prazno polje")
    @Size(min = 3, max = 110, message = "Naziv slike proizvoda mora biti minimum 3 karaktera i ne duži od 110 karaktera")
    @Column(name = "nazivSlike", nullable = false, length = 110)
    private String nazivSlike;

    @ManyToOne
    @JoinColumn(name = "proizvodId", referencedColumnName = "proizvodId", nullable = false)
    //@JsonIgnoreProperties("listaSlikaProizvoda")
    @JsonBackReference
    private Proizvod proizvod;

    public Integer getSlikaProizvodId() {
        return slikaProizvodId;
    }

    public void setSlikaProizvodId(Integer slikaProizvodId) {
        this.slikaProizvodId = slikaProizvodId;
    }

    public String getNazivSlike() {
        return nazivSlike;
    }

    public void setNazivSlike(String nazivSlike) {
        this.nazivSlike = nazivSlike;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
    }

    @Transient // The field marked with @Transient is IGNORED by mapping framework and the field not mapped to any database column
    public String getImagePath(){
        if(nazivSlike == null || proizvod == null){
            return null;
        }
        return "/slikeProizvodi/proizvodId-" + proizvod.getProizvodId() + "/" + nazivSlike;
    }

}
