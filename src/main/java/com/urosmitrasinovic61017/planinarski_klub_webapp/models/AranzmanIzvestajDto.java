package com.urosmitrasinovic61017.planinarski_klub_webapp.models;

public class AranzmanIzvestajDto {
    
    private Integer aranzmanId;
    private String nazivAranzmana;
    private String thumbnailPhoto;
    private Integer cena;
    private Long brojPutnikaZaAranzman; //po ovome vidimo jedan od parametara trazenosti aranzmana (suma brojPrijavljenih kolone svih putovanja za aranzman)
    private Double prosekPopunjenostiAranzmana; //srednja vrednost popunjenostiPutovanja za taj aranzman (drugi od parametara trazenosti aranzmana) (u %)
    private Long ukupnaZaradaAranzmana; //suma ukupnih zarada njegovih putovanja (najprofitabilniji aranzmani)
    private VrstaPutovanja vrstaPutovanjaAranzmana;
    private Double prosecnaZaradaPoPutnikuAranzmana; //srednja vrednost (avg) prosecne zarade po putniku svih putovanja tog aranzmana

    public AranzmanIzvestajDto(Integer aranzmanId, String nazivAranzmana, String thumbnailPhoto, Integer cena, Long brojPutnikaZaAranzman,
                               Double prosekPopunjenostiAranzmana, Long ukupnaZaradaAranzmana, VrstaPutovanja vrstaPutovanjaAranzmana,
                               Double prosecnaZaradaPoPutnikuAranzmana){

        this.aranzmanId = aranzmanId;
        this.nazivAranzmana = nazivAranzmana;
        this.thumbnailPhoto = thumbnailPhoto;
        this.cena = cena;
        this.brojPutnikaZaAranzman = brojPutnikaZaAranzman;
        this.prosekPopunjenostiAranzmana = prosekPopunjenostiAranzmana;
        this.ukupnaZaradaAranzmana = ukupnaZaradaAranzmana;
        this.vrstaPutovanjaAranzmana = vrstaPutovanjaAranzmana;
        this.prosecnaZaradaPoPutnikuAranzmana = prosecnaZaradaPoPutnikuAranzmana;

    }

    public Integer getAranzmanId() {
        return aranzmanId;
    }

    public void setAranzmanId(Integer aranzmanId) {
        this.aranzmanId = aranzmanId;
    }

    public String getNazivAranzmana() {
        return nazivAranzmana;
    }

    public void setNazivAranzmana(String nazivAranzmana) {
        this.nazivAranzmana = nazivAranzmana;
    }

    public String getThumbnailPhoto() {
        return thumbnailPhoto;
    }

    public void setThumbnailPhoto(String thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Long getBrojPutnikaZaAranzman() {
        return brojPutnikaZaAranzman;
    }

    public void setBrojPutnikaZaAranzman(Long brojPutnikaZaAranzman) {
        this.brojPutnikaZaAranzman = brojPutnikaZaAranzman;
    }

    public Double getProsekPopunjenostiAranzmana() {
        return prosekPopunjenostiAranzmana;
    }

    public void setProsekPopunjenostiAranzmana(Double prosekPopunjenostiAranzmana) {
        this.prosekPopunjenostiAranzmana = prosekPopunjenostiAranzmana;
    }

    public Long getUkupnaZaradaAranzmana() {
        return ukupnaZaradaAranzmana;
    }

    public void setUkupnaZaradaAranzmana(Long ukupnaZaradaAranzmana) {
        this.ukupnaZaradaAranzmana = ukupnaZaradaAranzmana;
    }

    public VrstaPutovanja getVrstaPutovanjaAranzmana() {
        return vrstaPutovanjaAranzmana;
    }

    public void setVrstaPutovanjaAranzmana(VrstaPutovanja vrstaPutovanjaAranzmana) {
        this.vrstaPutovanjaAranzmana = vrstaPutovanjaAranzmana;
    }

    public Double getProsecnaZaradaPoPutnikuAranzmana() {
        return prosecnaZaradaPoPutnikuAranzmana;
    }

    public void setProsecnaZaradaPoPutnikuAranzmana(Double prosecnaZaradaPoPutnikuAranzmana) {
        this.prosecnaZaradaPoPutnikuAranzmana = prosecnaZaradaPoPutnikuAranzmana;
    }
}
