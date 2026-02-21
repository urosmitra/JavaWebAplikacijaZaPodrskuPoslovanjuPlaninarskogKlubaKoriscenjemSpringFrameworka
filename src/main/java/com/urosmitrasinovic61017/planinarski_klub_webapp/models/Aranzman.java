package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.BrojPutnikaMinMaxValid;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "aranzman")
@BrojPutnikaMinMaxValid
public class Aranzman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@NotNull(message = "Id aranžmana ne može biti null")
    //NE SME BITI JAVA BEAN VALIDACIJE NA POLJU KOJE SE AUTOMATSKI GENERISEE!!!
    @Column(name = "aranzmanId", nullable = false)
    private Integer aranzmanId;

    @NotBlank(message = "Naziv aranžmana ne sme biti prazan")
    @Size(min = 3, max = 60, message = "Naziv aranžmana ne sme kraći od 3 karaktera, niti duži od 60 karaktera")
    @Column(name = "naziv", nullable = false, length = 60)
    private String naziv;

    @Size(min = 3, max = 100, message = "Naziv thumbnail slike ne sme biti duži od 100 karaktera")
    @Column(name = "thumbnailPhoto", length = 100)
    private String thumbnailPhoto;

    @NotBlank(message = "Kratak opis/slogan aranžmana ne sme biti prazan")
    @Size(min = 3, max = 200, message = "Kratak opis/slogan aranžmana ne sme biti kraći od 3 karaktera, niti duži od 200 karaktera")
    @Column(name = "kratakOpis", nullable = false, length = 200)
    private String kratakOpis; //u jednoj recenici kratki opis rute ili tagline putovanja

    @NotBlank(message = "Opis aranžmana ne sme biti prazan")
    @Size(min = 25, message = "Minimalan broj karaktera u opisu mora biti 25")
    @Column(name = "opis", columnDefinition = "LONGTEXT NOT NULL")
    private String opis;

    @NotBlank(message = "Plan puta ne sme biti prazno polje")
    @Size(min = 10, message = "Unesite minimalan broj karaktera (10)")
    @Column(name = "planPuta", columnDefinition = "LONGTEXT NOT NULL")
    private String planPuta;

    @NotBlank(message = "Organizacija ne sme biti prazno polje")
    @Size(min = 10, message = "Unesite minimalan broj karaktera (10)")
    @Column(name = "organizacija", columnDefinition = "LONGTEXT NOT NULL")
    private String organizacija;

    @Column(name = "napomene", columnDefinition = "TEXT")
    private String napomene; //dodatne napomene moze biti null


    @NotBlank(message = "Polje za prevoz ne sme biti prazno")
    @Size(min = 2, max = 90, message = "Polje za prevoz ne sme biti duže od 50 karaktera")
    @Column(name = "prevoz", nullable = false, length = 90)
    private String prevoz;

    @NotNull(message = "Cena ne može biti null")
    @Positive(message = "Cena mora biti pozitivan broj")
    @Column(name = "cena", nullable = false)
    private Integer cena;

    @NotBlank(message = "Polje zahtevnost je obavezno")
    @Pattern(regexp = "^[1-5]$", message = "Polje zahtevnost može biti broj između 1 i 5")
    @Column(name = "zahtevnost", nullable = false, length = 1)
    private String zahtevnost;

    @NotNull(message = "Polje za minimalan broj putnika ne sme biti null")
    @Min(value = 4, message = "Minimalan broj putnika ne sme biti manji od 4")
    @Column(name = "min_putnika", nullable = false)
    private Integer minPutnika;

    @NotNull(message = "Polja za maksimalan broj putnika ne sme biti null")
    @Min(value = 5, message = "Maksimalan broj putnika ne sme biti manji od 5")
    @Max(value = 60, message = "Maksimalan broj putnika po aranžmanu ne može biti viši od 60")
    @Column(name = "max_putnika", nullable = false)
    private Integer maxPutnika;

    @NotNull(message = "Polje za trajanje (broj dana) ne sme biti null")
    @Positive(message = "Trajanje u danima mora biti pozitivan broj")
    @Column(name = "trajanjeDani", nullable = false)
    private Integer trajanjeDani;

    @ManyToOne
    @JoinColumn(name = "vrstaPutovanjaId", referencedColumnName = "vrstaPutovanjaId")
    @JsonIgnoreProperties("listaAranzmana")
    private VrstaPutovanja vrstaPutovanja;


    @OneToMany(mappedBy = "aranzman", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("aranzman")
    private Set<Putovanje> listaPutovanja;

    @OneToMany(mappedBy = "aranzman", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("aranzman")
    private Set<SlikaAranzman> listaSlikaAranzmana;



    public Integer getAranzmanId() {
        return aranzmanId;
    }

    public void setAranzmanId(Integer aranzmanId) {
        this.aranzmanId = aranzmanId;
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
        return "/slikeAranzmani/aranzmanId-" + aranzmanId + "/" + thumbnailPhoto;
    }

    public String getKratakOpis() {
        return kratakOpis;
    }

    public void setKratakOpis(String kratakOpis) {
        this.kratakOpis = kratakOpis;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getPlanPuta() {
        return planPuta;
    }

    public void setPlanPuta(String planPuta) {
        this.planPuta = planPuta;
    }

    public String getOrganizacija() {
        return organizacija;
    }

    public void setOrganizacija(String organizacija) {
        this.organizacija = organizacija;
    }

    public String getNapomene() {
        return napomene;
    }

    public void setNapomene(String napomene) {
        this.napomene = napomene;
    }

    public String getPrevoz() {
        return prevoz;
    }

    public void setPrevoz(String prevoz) {
        this.prevoz = prevoz;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public String getZahtevnost() {
        return zahtevnost;
    }

    public void setZahtevnost(String zahtevnost) {
        this.zahtevnost = zahtevnost;
    }

    public Integer getMinPutnika() {
        return minPutnika;
    }

    public void setMinPutnika(Integer minPutnika) {
        this.minPutnika = minPutnika;
    }

    public Integer getMaxPutnika() {
        return maxPutnika;
    }

    public void setMaxPutnika(Integer maxPutnika) {
        this.maxPutnika = maxPutnika;
    }

    public Integer getTrajanjeDani() {
        return trajanjeDani;
    }

    public void setTrajanjeDani(Integer trajanjeDani) {
        this.trajanjeDani = trajanjeDani;
    }

    public VrstaPutovanja getVrstaPutovanja() {
        return vrstaPutovanja;
    }

    public void setVrstaPutovanja(VrstaPutovanja vrstaPutovanja) {
        this.vrstaPutovanja = vrstaPutovanja;
    }

    public Set<Putovanje> getListaPutovanja() {
        return listaPutovanja;
    }

    public void setListaPutovanja(Set<Putovanje> listaPutovanja) {
        this.listaPutovanja = listaPutovanja;
    }

    public Set<SlikaAranzman> getListaSlikaAranzmana() {
        return listaSlikaAranzmana;
    }

    public void setListaSlikaAranzmana(Set<SlikaAranzman> listaSlikaAranzmana) {
        this.listaSlikaAranzmana = listaSlikaAranzmana;
    }

}
