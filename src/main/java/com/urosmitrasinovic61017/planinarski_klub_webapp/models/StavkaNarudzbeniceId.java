package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StavkaNarudzbeniceId implements Serializable {


    private Integer narudzbenicaId;

    private Integer proizvodId;

    public StavkaNarudzbeniceId(){

    }


    public StavkaNarudzbeniceId(Integer narudzbenicaId, Integer proizvodId) {
        this.narudzbenicaId = narudzbenicaId;
        this.proizvodId = proizvodId;
    }

    //getters and setters
    public Integer getNarudzbenicaId() {
        return narudzbenicaId;
    }

    public void setNarudzbenicaId(Integer narudzbenicaId) {
        this.narudzbenicaId = narudzbenicaId;
    }

    public Integer getProizvodId() {
        return proizvodId;
    }

    public void setProizvodId(Integer proizvodId) {
        this.proizvodId = proizvodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StavkaNarudzbeniceId that = (StavkaNarudzbeniceId) o;
        return Objects.equals(narudzbenicaId, that.narudzbenicaId) &&
                Objects.equals(proizvodId, that.proizvodId);
    }

    

    @Override
    public int hashCode() {
        return Objects.hash(narudzbenicaId, proizvodId);
    }
}
