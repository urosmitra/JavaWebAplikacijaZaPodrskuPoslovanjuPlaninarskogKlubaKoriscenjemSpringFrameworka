package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "slika_aranzman")
public class SlikaAranzman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slikaId", nullable = false)
    private Integer slikaId;

    @NotBlank(message = "Putanja slike ne sme biti prazno polje")
    @Size(min = 3, max = 150, message = "Naziv slike aranzmana ne sme biti veci od 150 karaktera")
    @Column(name = "nazivSlike", nullable = false, length = 150)
    private String nazivSlike;

    @ManyToOne
    @JoinColumn(name = "aranzmanId", referencedColumnName = "aranzmanId", nullable = false)
    @JsonIgnoreProperties("listaSlikaAranzmana")
    private Aranzman aranzman;

    public Integer getSlikaId() {
        return slikaId;
    }

    public void setSlikaId(Integer slikaId) {
        this.slikaId = slikaId;
    }

    public String getNazivSlike() {
        return nazivSlike;
    }

    public void setNazivSlike(String nazivSlike) {
        this.nazivSlike = nazivSlike;
    }

    public Aranzman getAranzman() {
        return aranzman;
    }

    public void setAranzman(Aranzman aranzman) {
        this.aranzman = aranzman;
    }


    @Transient // The field marked with @Transient is IGNORED by mapping framework and the field not mapped to any database column
    public String getImagePath(){
        if(nazivSlike == null || aranzman == null){
            return  null;
        }
        return "/slikeAranzmani/aranzmanId-" + aranzman.getAranzmanId() + "/" + nazivSlike;
    }



}
