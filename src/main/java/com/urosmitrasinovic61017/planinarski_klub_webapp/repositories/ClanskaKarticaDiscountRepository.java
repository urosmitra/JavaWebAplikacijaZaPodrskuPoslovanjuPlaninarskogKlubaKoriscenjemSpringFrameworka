package com.urosmitrasinovic61017.planinarski_klub_webapp.repositories;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ClanskaKarticaDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClanskaKarticaDiscountRepository extends JpaRepository<ClanskaKarticaDiscount, Integer> {

    @Query(value = "SELECT ckd FROM ClanskaKarticaDiscount ckd WHERE ckd.vrednostBodovi < :brojBodova")
    List<ClanskaKarticaDiscount> findAllAvailableDiscountPackagesPoBrojuBodova(@Param("brojBodova") Integer brojBodova);

}
