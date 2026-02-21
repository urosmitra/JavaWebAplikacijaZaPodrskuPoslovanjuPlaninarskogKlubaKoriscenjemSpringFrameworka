package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ClanskaKarticaDiscount;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.ClanskaKarticaDiscountRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ClanskaKarticaDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClanskaKarticaDiscountServiceImpl implements ClanskaKarticaDiscountService {

    @Autowired
    private ClanskaKarticaDiscountRepository clanKartDiscountRepo;

    @Override
    public List<ClanskaKarticaDiscount> getAllClanskaKarticaDiscountNagrade() {
        return this.clanKartDiscountRepo.findAll();
    }

    @Override
    public ClanskaKarticaDiscount save(ClanskaKarticaDiscount clanskaKarticaDiscount) {
        return this.clanKartDiscountRepo.save(clanskaKarticaDiscount);
    }

    @Override
    public ClanskaKarticaDiscount update(ClanskaKarticaDiscount clanskaKarticaDiscount) {
        return this.clanKartDiscountRepo.save(clanskaKarticaDiscount);
    }

    @Override
    public void deleteById(Integer clanKartDiscountId) {
        this.clanKartDiscountRepo.deleteById(clanKartDiscountId);
    }

    @Override
    public Optional<ClanskaKarticaDiscount> findById(Integer id) {
        return this.clanKartDiscountRepo.findById(id);
    }

    @Override
    public List<ClanskaKarticaDiscount> findAllAvailableDiscountPackages(Integer brojBodovaClanskeKartice) {
        return this.clanKartDiscountRepo.findAllAvailableDiscountPackagesPoBrojuBodova(brojBodovaClanskeKartice);
    }

    @Override
    public Integer izracunajDiscountedValueOfPrice(Integer nagradniPopust, Integer cena) {
        cena = cena - (cena * nagradniPopust/100);
        return cena;
    }
}
