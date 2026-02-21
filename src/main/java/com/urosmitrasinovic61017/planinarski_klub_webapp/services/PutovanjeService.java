package com.urosmitrasinovic61017.planinarski_klub_webapp.services;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.BuducaPutovanjaIzvestajDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ProslaPutovanjaIzvestajDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Putovanje;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.PutovanjeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PutovanjeService {

    List<Putovanje> getAllPutovanja();

    Putovanje save(Putovanje putovanje);

    Putovanje update(Putovanje putovanje);

    void deleteById(Integer id);

    Optional<Putovanje> findById(Integer id);

    List<Putovanje> getAllPutovanjaAranzmana(Integer aranzmanId);

    List<Putovanje> getAllBuducaPutovanjaAranzmana(Integer aranzmanId);

    List<Putovanje> getAllPutovanjaUTokuPotvrdjeno(Integer aranzmanId);

    Integer cenaPutovanjaAranzmana(Integer cenaAranzmana ,Integer popustPutovanja);

    Integer getNajmanjaCenaPutovanjaAranzmana(Integer aranzmanId);

    boolean daLiImaPopustaZaAranzman(Integer aranzmanId);

    List<Putovanje> getIstorijaPutovanjaOdrzanih(Integer aranzmanId);

    List<Putovanje> getOtkazanaPutovanjaAranzmana(Integer aranzmanId);

    List<PutovanjeStatus> getListaStatusaPutovanja();

    void incrementBrojPrijavljenih(Integer putovanjeId);

    void smanjiBrojPrijavljenih(Integer putovanjeId);

    boolean podesavanjeStatusaPutovanjaPotvrdjeno(Putovanje putovanje);

    boolean podesavanjeStatusaPutovanjaNepotvrdjeno(Putovanje putovanje);

    List<Putovanje> getBuducaPutovanjaLimitirano();

    List<Putovanje> getAllBuducaPutovanjaAranzmanaAvailableZaKorisnika(Integer aranzmanId);

    Integer getBrojSlobodnihMestaPutovanja(Putovanje putovanje);

    Page<BuducaPutovanjaIzvestajDto> getBuducaPutovanjaIzvestajePagination(Pageable pageable);

    Page<BuducaPutovanjaIzvestajDto> findPaginatedBuducaPutovanjaIzvestaje(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<ProslaPutovanjaIzvestajDto> getProslaPutovanjaIzvestajePagination(Pageable pageable);

    Page<ProslaPutovanjaIzvestajDto> findPaginatedProslaPutovanjaIzvestaje(int pageNo, int pageSize, String sortField, String sortDirection);

}
