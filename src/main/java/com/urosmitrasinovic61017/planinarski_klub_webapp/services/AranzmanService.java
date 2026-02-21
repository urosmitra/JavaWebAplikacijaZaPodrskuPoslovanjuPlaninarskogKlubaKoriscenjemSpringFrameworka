package com.urosmitrasinovic61017.planinarski_klub_webapp.services;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Aranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.AranzmanIzvestajDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AranzmanService {

    List<Aranzman> getAllAranzmani();

    Aranzman save(Aranzman aranzman);

    Aranzman update(Aranzman aranzman);

    void deleteById(Integer id);

    Optional<Aranzman> findById(Integer id);

    Optional<Aranzman> findByNaziv(String naziv);

    List<String> getListaZahtevnosti();

    void updateThumbnailPhoto_If_Added(Aranzman aranzman, MultipartFile multipartFile) throws IOException;

    List<Aranzman> findByVrstaPutovanjaVrstaPutovanjaId(Integer vrstaPutovanjaId);

    Page<AranzmanIzvestajDto> getAranzmanIzvestajeZaMenadzeraPagination(Pageable pageable);

    Page<AranzmanIzvestajDto> findPaginatedAranzmanIzvestaje(int pageNo, int pageSize, String sortField, String sortDirection);


}
