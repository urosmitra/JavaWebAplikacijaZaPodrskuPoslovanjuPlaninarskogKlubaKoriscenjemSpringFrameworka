package com.urosmitrasinovic61017.planinarski_klub_webapp.services;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ProizvodIzvestajDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProizvodService {

    List<Proizvod> getAllProizvodi();

    Proizvod save(Proizvod proizvod);

    Proizvod update(Proizvod proizvod);

    void deleteById(Integer proizvodId);

    Optional<Proizvod> findById(Integer proizvodId);

    void updateThumbnailPhoto_If_Added(Proizvod proizvod, MultipartFile multipartFile) throws IOException;

    Optional<Proizvod> findByNaziv(String naziv);

    Integer getCenaProizvodaSaPopustom(Proizvod proizvod);

    List<Proizvod> findByVrstaProizvodaVrstaProizvodaId(Integer vrstaProizvodaId);

    boolean daLiImaNaStanju (Proizvod proizvod);

    List<Integer> napuniBrojUKoliciniDropDown(Proizvod proizvod);

    void smanjiKolicinuUMagacinu(Proizvod proizvod, Integer kolicina);

    List<Proizvod> findByKeyword(String keyword);

    List<Proizvod> orderByPriceAscDesc(String orderDirection);

    Page<ProizvodIzvestajDto> getProizvodIzvestajZaMenadzeraPagination(Pageable pageable);

    Page<ProizvodIzvestajDto> findPaginatedProizvodIzvestaje(int pageNo, int pageSize, String sortField, String sortDirection);





}
