package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.ExcludeGson;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

/*@NamedNativeQuery(name = "Proizvod.getProizvodIzvestajZaMenadzeraPagination",
                  query = "SELECT p.proizvodId as proizvodId, p.naziv as naziv, p.cena as cena, p.popust as popust, " +
                          "(CASE WHEN p.popust = 0 THEN p.cena ELSE FLOOR(p.cena - p.cena * p.popust/100) END) as cenaSaPopustom," +
                          "p.kolicina as dostupnaKolicina, SUM(sn.izabranaKolicina) as brojProdatihJedinica, SUM(sn.ukupnaCenaStavke) as ukupnaZaradaEvri, " +
                          "vp.naziv as vrstaProizvoda FROM proizvod p LEFT JOIN stavka_narudzbenice sn ON p.proizvodId = sn.proizvodId JOIN vrsta_proizvoda vp ON p.vrstaProizvodaId = vp.vrstaProizvodaId GROUP BY p.proizvodId",
                  resultSetMapping = "Mapping.ProizvodIzvestajDto")*/

/*@SqlResultSetMapping(name = "Mapping.ProizvodIzvestajDto",
                    classes = @ConstructorResult(targetClass = ProizvodIzvestajDto.class,
                                                 columns = {@ColumnResult(name = "proizvodId", type = Integer.class),
                                                            @ColumnResult(name = "naziv", type = String.class),
                                                            @ColumnResult(name = "cena", type = Integer.class),
                                                            @ColumnResult(name = "popust", type = Integer.class),
                                                            @ColumnResult(name = "cenaSaPopustom", type = Integer.class),
                                                            @ColumnResult(name = "dostupnaKolicina", type = Integer.class),
                                                            @ColumnResult(name = "brojProdatihJedinica", type = Integer.class),
                                                            @ColumnResult(name = "ukupnaZaradaEvri", type = Integer.class),
                                                            @ColumnResult(name = "vrstaProizvoda", type = String.class)}))*/


@Entity
@Table(name = "proizvod")
public class Proizvod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proizvodId", nullable = false)
    private Integer proizvodId;

    @NotBlank(message = "Naziv proizvoda ne sme biti prazno polje")
    @Size(min = 3, max = 150, message = "Naziv proizvoda ne sme biti manji od 3 karaktera, niti veći od 150 karaktera")
    @Column(name = "naziv", nullable = false, length = 150)
    private String naziv;

    @NotBlank(message = "Opis proizvoda ne sme biti prazan")
    @Size(min = 20, message = "Minimalan broj karaktera u opisu proizvoda mora biti 20")
    @Column(name = "opis", nullable = false, columnDefinition = "LONGTEXT NOT NULL")
    private String opis;

    @Pattern(regexp = "^|.{20,}$", message = "Ostavite prazno ili unesite minimalan broj karaktera (20)")
    @Column(name = "tehnickeInfo", columnDefinition = "LONGTEXT")
    private String tehnickeInfo; //moze biti null

    @NotNull(message = "Kolicina ne sme biti null")
    @PositiveOrZero(message = "Polje za količinu ne sme biti negativno")
    @Column(name = "kolicina", nullable = false)
    private Integer kolicina;

    @NotNull(message = "Polje za cenu ne sme biti null")
    @Positive(message = "Cena mora biti pozitivan broj")
    @Column(name = "cena", nullable = false)
    private Integer cena;

    @NotNull(message = "Polje za popust ne sme biti null (ukoliko ga nema staviti 0)")
    @Max(value = 80, message = "Maksimalni popust koji možete postaviti je 80")
    @Min(value = 0, message = "Popust mora biti pozitivan broj (minimum 0)")
    @Column(name = "popust", nullable = false)
    private Integer popust;

    //@NotBlank(message = "Putanja slike ne sme biti prazno polje")
    @Size(min = 3, max = 150, message = "Polje za putanju slike ne sme biti duže od 150 karaktera")
    @Column(name = "thumbnailPhoto", length = 150)
    private String thumbnailPhoto;

    @ManyToOne
    @JoinColumn(name = "vrstaProizvodaId", referencedColumnName = "vrstaProizvodaId")
    //@JsonIgnoreProperties("listaProizvoda")
    @JsonBackReference
    private VrstaProizvoda vrstaProizvoda; //moze biti null

    @Formula(value = "round(cena * (1-popust/100))")
    private Integer cenaSaPopustom;

    @ExcludeGson
    @OneToMany(mappedBy = "proizvod", cascade = CascadeType.ALL)
    //@JsonIgnoreProperties("proizvod")
    @JsonManagedReference
    private List<StavkaNarudzbenice> listaStavkiNarudzbeniceProizvoda;

    @ExcludeGson
    @OneToMany(mappedBy = "proizvod", cascade = CascadeType.ALL)
    //@JsonIgnoreProperties("proizvod")
    @JsonManagedReference
    private List<StavkaKorpe> listaStavkiKorpeProizvoda;

    @ExcludeGson
    @OneToMany(mappedBy = "proizvod", cascade = CascadeType.ALL)
    //@JsonIgnoreProperties("proizvod")
    @JsonManagedReference
    private List<SlikaProizvod> listaSlikaProizvoda;


    //getters and setters
    public Integer getProizvodId() {
        return proizvodId;
    }

    public void setProizvodId(Integer proizvodId) {
        this.proizvodId = proizvodId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getTehnickeInfo() {
        return tehnickeInfo;
    }

    public void setTehnickeInfo(String tehnickeInfo) {
        this.tehnickeInfo = tehnickeInfo;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Integer getPopust() {
        return popust;
    }

    public void setPopust(Integer popust) {
        this.popust = popust;
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
        return "/slikeProizvodi/proizvodId-" + proizvodId + "/" + thumbnailPhoto;
    }


    public VrstaProizvoda getVrstaProizvoda() {
        return vrstaProizvoda;
    }

    public void setVrstaProizvoda(VrstaProizvoda vrstaProizvoda) {
        this.vrstaProizvoda = vrstaProizvoda;
    }

    public List<StavkaNarudzbenice> getListaStavkiNarudzbeniceProizvoda() {
        return listaStavkiNarudzbeniceProizvoda;
    }

    public void setListaStavkiNarudzbeniceProizvoda(List<StavkaNarudzbenice> listaStavkiNarudzbeniceProizvoda) {
        this.listaStavkiNarudzbeniceProizvoda = listaStavkiNarudzbeniceProizvoda;
    }

    public List<StavkaKorpe> getListaStavkiKorpeProizvoda() {
        return listaStavkiKorpeProizvoda;
    }

    public void setListaStavkiKorpeProizvoda(List<StavkaKorpe> listaStavkiKorpeProizvoda) {
        this.listaStavkiKorpeProizvoda = listaStavkiKorpeProizvoda;
    }

    public List<SlikaProizvod> getListaSlikaProizvoda() {
        return listaSlikaProizvoda;
    }

    public void setListaSlikaProizvoda(List<SlikaProizvod> listaSlikaProizvoda) {
        this.listaSlikaProizvoda = listaSlikaProizvoda;
    }

    public Integer getCenaSaPopustom() {
        return cenaSaPopustom;
    }

}
