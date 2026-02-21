package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.DatumPolaskaIPovratkaValid;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "putovanje")
@DatumPolaskaIPovratkaValid
public class Putovanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "putovanjeId", nullable = false)
    private Integer putovanjeId;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy. HH:mm")
    @NotNull(message = "Datum i vreme polaska ne sme biti null", groups = PutovanjeGroup.class)
    @Future(message = "Datum i Vreme polaska mora biti u budućnosti", groups = PutovanjeGroup.class)
    @Column(name = "datumVremePolaska", nullable = false)
    private LocalDateTime datumVremePolaska;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Polje datum povratka ne sme biti null", groups = PutovanjeGroup.class)
    @Future(message = "Datum koji ste izabrali mora biti budući", groups = PutovanjeGroup.class)
    @Column(name = "datumPovratka", nullable = false)
    private LocalDate datumPovratka;

    @NotNull(message = "Polje za popust ne sme biti null (ukoliko ga nema staviti 0)", groups = PutovanjeGroup.class)
    @Max(value = 80, message = "Maksimalni popust koji možete postaviti je 80", groups = PutovanjeGroup.class)
//    @Pattern(regexp = "^[1-9]|[1-7][0-9]|80$", message = "Popust mora biti pozitivan broj") ***Pattern anotacija ne ide uz Integer tip podataka
    @Min(value = 0, message = "Popust mora biti pozitivan broj (minimum 0)", groups = PutovanjeGroup.class)
    @Column(name = "popust", nullable = false)
    private Integer popust;

    @NotNull(message = "Broj prijavljenih ne sme biti null", groups = PutovanjeGroup.class)
    @Column(name = "brojPrijavljenih", nullable = false)
    private Integer brojPrijavljenih;

    @NotNull(message = "Polje status ne sme biti null", groups = PutovanjeGroup.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private PutovanjeStatus status;

    //@Size(min = 10, message = "Unesite minimalan broj karaktera (10)", groups = IzvestajGroup.class)
    @Pattern(regexp = "^|.{10,}$", message = "Ostavite prazno ili unesite minimalan broj karaktera (10)", groups = IzvestajGroup.class)
    @Column(name = "izvestaj", columnDefinition = "TEXT")
    private String izvestaj;


    @ManyToOne
    @JoinColumn(name = "aranzmanId", referencedColumnName = "aranzmanId", nullable = false)
    @JsonIgnoreProperties("listaPutovanja")
    private Aranzman aranzman;


    @OneToMany(mappedBy = "putovanje", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("putovanje")
    private Set<RezervacijaPutovanja> listaRezervacija;

    @Formula("(select round((brojPrijavljenih/a.max_putnika) * 100) FROM aranzman a where a.aranzmanId = aranzmanId)")
    private Integer popunjenostPutovanja;

    @Formula(value = "(select round(a.cena * (1-popust/100)) FROM aranzman a WHERE a.aranzmanId = aranzmanId)")
    private Integer cenaSaPopustom;

    @Formula(value = "(select round(AVG(rez.cenaPutovanja), 2) FROM rezervacija_putovanja rez where rez.putovanjeId = putovanjeId and status NOT IN ('OTKAZANO', 'NEPOTVRDJENO') GROUP BY putovanjeId)")
    private Double prosecnaZaradaPoPutniku;
    
    @Formula(value = "(select SUM(rez.cenaPutovanja) FROM rezervacija_putovanja rez where rez.putovanjeId = putovanjeId and status NOT IN ('OTKAZANO', 'NEPOTVRDJENO') GROUP BY putovanjeId)")
    private Long ukupnaZarada;




    public Integer getPutovanjeId() {
        return putovanjeId;
    }

    public void setPutovanjeId(Integer putovanjeId) {
        this.putovanjeId = putovanjeId;
    }

    public LocalDateTime getDatumVremePolaska() {
        return datumVremePolaska;
    }

    public void setDatumVremePolaska(LocalDateTime datumVremePolaska) {
        this.datumVremePolaska = datumVremePolaska;
    }

    public LocalDate getDatumPovratka() {
        return datumPovratka;
    }

    public void setDatumPovratka(LocalDate datumPovratka) {
        this.datumPovratka = datumPovratka;
    }

    public Integer getPopust() {
        return popust;
    }

    public void setPopust(Integer popust) {
        this.popust = popust;
    }

    public Integer getBrojPrijavljenih() {
        return brojPrijavljenih;
    }

    public void setBrojPrijavljenih(Integer brojPrijavljenih) {
        this.brojPrijavljenih = brojPrijavljenih;
    }

    public PutovanjeStatus getStatus() {
        return status;
    }

    public void setStatus(PutovanjeStatus status) {
        this.status = status;
    }

    public String getIzvestaj() {
        return izvestaj;
    }

    public void setIzvestaj(String izvestaj) {
        this.izvestaj = izvestaj;
    }

    public Aranzman getAranzman() {
        return aranzman;
    }

    public void setAranzman(Aranzman aranzman) {
        this.aranzman = aranzman;
    }

    public Set<RezervacijaPutovanja> getListaRezervacija() {
        return listaRezervacija;
    }

    public void setListaRezervacija(Set<RezervacijaPutovanja> listaRezervacija) {
        this.listaRezervacija = listaRezervacija;
    }

    public Integer getPopunjenostPutovanja() {
        return popunjenostPutovanja;
    }

    public Integer getCenaSaPopustom() {
        return cenaSaPopustom;
    }

    public Double getProsecnaZaradaPoPutniku() {
        return prosecnaZaradaPoPutniku;
    }

    public Long getUkupnaZarada() {
        return ukupnaZarada;
    }
}
