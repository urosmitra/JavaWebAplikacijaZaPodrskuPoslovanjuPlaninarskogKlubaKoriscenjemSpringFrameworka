package com.urosmitrasinovic61017.planinarski_klub_webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "vrsta_putovanja")
public class VrstaPutovanja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vrstaPutovanjaId", nullable = false)
    private Integer vrstaPutovanjaId;

    @NotBlank(message = "Naziv vrste putovanje ne sme biti prazno polje")
    @Size(min = 3, message = "Unesite minimalan broj karaktera za polje (3)")
    @Column(name = "naziv", nullable = false, length = 90)
    private String naziv;

    @Size(min = 3, max = 100, message = "Naziv thumbnail slike ne sme biti duži od 100 karaktera")
    @Column(name = "thumbnailPhoto", length = 100)
    private String thumbnailPhoto;

    @NotBlank(message = "Opis aranžmana ne sme biti prazan")
    @Size(min = 10, message = "Minimalan broj karaktera u opisu mora biti 10")
    @Column(name = "opis", columnDefinition = "LONGTEXT NOT NULL")
    private String opis;



    @OneToMany(mappedBy = "vrstaPutovanja")
    @JsonIgnoreProperties("vrstaPutovanja")
    private Set<Aranzman> listaAranzmana;


    public Integer getVrstaPutovanjaId() {
        return vrstaPutovanjaId;
    }

    public void setVrstaPutovanjaId(Integer vrstaPutovanjaId) {
        this.vrstaPutovanjaId = vrstaPutovanjaId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getThumbnailPhoto() {
        return thumbnailPhoto;
    }

    public void setThumbnailPhoto(String thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
    }

    @Transient // The field marked with @Transient is IGNORED by mapping framework and the field not mapped to any database column
    public String getThumbnailPhotoPath(){
        if(thumbnailPhoto == null){
            return  null;
        }
        return "/vrstePutovanja/vrstaPutovanjaId-" + vrstaPutovanjaId + "/" + thumbnailPhoto;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Set<Aranzman> getListaAranzmana() {
        return listaAranzmana;
    }

    public void setListaAranzmana(Set<Aranzman> listaAranzmana) {
        this.listaAranzmana = listaAranzmana;
    }
}
