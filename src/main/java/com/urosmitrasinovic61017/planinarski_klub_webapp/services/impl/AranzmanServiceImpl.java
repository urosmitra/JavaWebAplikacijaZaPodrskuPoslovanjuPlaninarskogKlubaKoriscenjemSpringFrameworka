package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;


import com.urosmitrasinovic61017.planinarski_klub_webapp.config.FileUploadUtil;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Aranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.AranzmanIzvestajDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.AranzmanRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.AranzmanService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AranzmanServiceImpl implements AranzmanService {

    @Autowired
    private AranzmanRepository aranzmanRepository;


    @Override
    public List<Aranzman> getAllAranzmani() {
        return this.aranzmanRepository.findAll();
    }

    @Override
    public Aranzman save(Aranzman aranzman) {
        return this.aranzmanRepository.save(aranzman);
    }

    @Override
    public Aranzman update(Aranzman aranzman) {
        return this.aranzmanRepository.save(aranzman);
    }

    @Override
    public void deleteById(Integer id) {
        this.aranzmanRepository.deleteById(id);
    }

    @Override
    public Optional<Aranzman> findById(Integer id) {
        return this.aranzmanRepository.findById(id);
    }

    @Override
    public Optional<Aranzman> findByNaziv(String naziv) {
        return this.aranzmanRepository.findByNaziv(naziv);
    }


    @Override
    public List<String> getListaZahtevnosti() {
        List<String> listaZahtevnosti = new ArrayList<>();
        listaZahtevnosti.add("1");
        listaZahtevnosti.add("2");
        listaZahtevnosti.add("3");
        listaZahtevnosti.add("4");
        listaZahtevnosti.add("5");

        return listaZahtevnosti;
    }

    @Override
    public void updateThumbnailPhoto_If_Added(Aranzman aranzman, MultipartFile multipartFile) throws IOException {
        if(multipartFile != null && !multipartFile.isEmpty()){

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String fileNameWithoutExt = FilenameUtils.removeExtension(fileName);
            String extention = FilenameUtils.getExtension(fileName);
            String fileNameFinal = fileNameWithoutExt + "T" + "." + extention;

            aranzman.setThumbnailPhoto(fileNameFinal);
            this.update(aranzman);

            String uploadDir = "slikeAranzmani/aranzmanId-" + aranzman.getAranzmanId();
            FileUploadUtil.saveFile(uploadDir, fileNameFinal, multipartFile);
        }
    }

    @Override
    public List<Aranzman> findByVrstaPutovanjaVrstaPutovanjaId(Integer vrstaPutovanjaId) {
        return this.aranzmanRepository.findByVrstaPutovanjaVrstaPutovanjaId(vrstaPutovanjaId);
    }

    @Override
    public Page<AranzmanIzvestajDto> getAranzmanIzvestajeZaMenadzeraPagination(Pageable pageable) {
        Page<AranzmanIzvestajDto> aranzmanIzvestaji = this.aranzmanRepository.getAllAranzmanIzvestajiZaMenadzera(pageable);
        return aranzmanIzvestaji;
    }

    @Override
    public Page<AranzmanIzvestajDto> findPaginatedAranzmanIzvestaje(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.getAranzmanIzvestajeZaMenadzeraPagination(pageable);
    }


}
