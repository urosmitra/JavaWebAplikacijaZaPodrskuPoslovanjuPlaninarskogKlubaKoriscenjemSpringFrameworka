package com.urosmitrasinovic61017.planinarski_klub_webapp.models;

public class ProizvodIzvestajDto {

    private Integer proizvodId;
    private String naziv;
    private Integer cena;
    private Integer popust;
    private Integer cenaSaPopustom;
    private Long brojProdatihJedinica;
    private Long ukupnaZaradaEvri;
    private Integer dostupnaKolicina;
    private VrstaProizvoda vrstaProizvoda;

    public ProizvodIzvestajDto(Integer proizvodId, String naziv, Integer cena, Integer popust, Integer cenaSaPopustom, Integer dostupnaKolicina, Long brojProdatihJedinica, Long ukupnaZaradaEvri, VrstaProizvoda vrstaProizvoda){
        this.proizvodId = proizvodId;
        this.naziv = naziv;
        this.cena = cena;
        this.popust = popust;
        this.cenaSaPopustom = cenaSaPopustom; //ideja: napraviti mozda transient polje u Proizvod modelu, i tu izracunati cenu sa popustom
        this.dostupnaKolicina = dostupnaKolicina;
        this.brojProdatihJedinica = brojProdatihJedinica;
        this.ukupnaZaradaEvri = ukupnaZaradaEvri;
        this.vrstaProizvoda = vrstaProizvoda;
        //(p.cena * (1-p.popust/100))
        //this.cenaSaPopustom = cena * (1-popust/100);
    }

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

    public Integer getCenaSaPopustom() {
        return cenaSaPopustom;
    }

    public void setCenaSaPopustom(Integer cenaSaPopustom) {
        this.cenaSaPopustom = cenaSaPopustom;
    }

    public Long getBrojProdatihJedinica() {
        return brojProdatihJedinica;
    }

    public void setBrojProdatihJedinica(Long brojProdatihJedinica) {
        this.brojProdatihJedinica = brojProdatihJedinica;
    }

    public Long getUkupnaZaradaEvri() {
        return ukupnaZaradaEvri;
    }

    public void setUkupnaZaradaEvri(Long ukupnaZaradaEvri) {
        this.ukupnaZaradaEvri = ukupnaZaradaEvri;
    }

    public Integer getDostupnaKolicina() {
        return dostupnaKolicina;
    }

    public void setDostupnaKolicina(Integer dostupnaKolicina) {
        this.dostupnaKolicina = dostupnaKolicina;
    }

    public VrstaProizvoda getVrstaProizvoda() {
        return vrstaProizvoda;
    }

    public void setVrstaProizvoda(VrstaProizvoda vrstaProizvoda) {
        this.vrstaProizvoda = vrstaProizvoda;
    }
}
