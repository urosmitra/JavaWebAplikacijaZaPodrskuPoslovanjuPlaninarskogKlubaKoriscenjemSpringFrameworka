package com.urosmitrasinovic61017.planinarski_klub_webapp.services;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ClanskaKarticaDiscount;

import java.util.List;
import java.util.Optional;

public interface ClanskaKarticaDiscountService {

    List<ClanskaKarticaDiscount> getAllClanskaKarticaDiscountNagrade();

    ClanskaKarticaDiscount save(ClanskaKarticaDiscount clanskaKarticaDiscount);

    ClanskaKarticaDiscount update(ClanskaKarticaDiscount clanskaKarticaDiscount);

    void deleteById(Integer clanKartDiscountId);

    Optional<ClanskaKarticaDiscount> findById(Integer id);

    List<ClanskaKarticaDiscount> findAllAvailableDiscountPackages(Integer brojBodovaClanskeKartice);

    Integer izracunajDiscountedValueOfPrice(Integer nagradniPopust, Integer cena);



}
