package com.urosmitrasinovic61017.planinarski_klub_webapp.models;


import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "clanska_kartica_discount")
public class ClanskaKarticaDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clanskaKartDiscountId", nullable = false)
    private Integer clanskaKartDiscountId;

    @NotBlank(message = "Opis ne sme biti prazno polje.")
    @Size(min = 5, max = 150, message = "Opis ne sme biti kraći od 5 karaktera niti duži 150 karaktera.")
    @Column(name = "opis", nullable = false, length = 150)
    private String opis;

    @NotNull(message = "Vrednost u bodovima polje ne sme biti null.")
    @Min(value = 4000, message = "Vrednost u bodovima ne sme biti manja od 4000")
    @Column(name = "vrednostBodovi", nullable = false)
    private Integer vrednostBodovi;

    @NotNull(message = "Polje za popust ne sme biti null.")
    @Min(value = 5, message = "Minimalan nagradni popust clanske kartice mora biti 5%")
    @Max(value = 25, message = "Maksimalan nagradni popust članske kartice ne sme biti veći od 25%")
    @Column(name = "popust", nullable = false)
    private Integer popust;

    public Integer getClanskaKartDiscountId() {
        return clanskaKartDiscountId;
    }

    public void setClanskaKartDiscountId(Integer clanskaKartDiscountId) {
        this.clanskaKartDiscountId = clanskaKartDiscountId;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getVrednostBodovi() {
        return vrednostBodovi;
    }

    public void setVrednostBodovi(Integer vrednostBodovi) {
        this.vrednostBodovi = vrednostBodovi;
    }

    public Integer getPopust() {
        return popust;
    }

    public void setPopust(Integer popust) {
        this.popust = popust;
    }
}
