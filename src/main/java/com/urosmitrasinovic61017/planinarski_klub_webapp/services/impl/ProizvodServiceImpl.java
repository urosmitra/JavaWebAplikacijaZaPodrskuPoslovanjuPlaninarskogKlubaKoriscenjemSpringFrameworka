package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;


import com.urosmitrasinovic61017.planinarski_klub_webapp.config.FileUploadUtil;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ProizvodIzvestajDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.VrstaProizvoda;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.ProizvodRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ProizvodService;
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
public class ProizvodServiceImpl implements ProizvodService {

    @Autowired
    private ProizvodRepository proizvodRepository;

    @Override
    public List<Proizvod> getAllProizvodi() {
        return this.proizvodRepository.findAll();
    }

    @Override
    public Proizvod save(Proizvod proizvod) {
        return this.proizvodRepository.save(proizvod);
    }

    @Override
    public Proizvod update(Proizvod proizvod) {
        return this.proizvodRepository.save(proizvod);
    }

    @Override
    public void deleteById(Integer proizvodId) {
        this.proizvodRepository.deleteById(proizvodId);
    }

    @Override
    public Optional<Proizvod> findById(Integer proizvodId) {
        return this.proizvodRepository.findById(proizvodId);
    }

    @Override
    public void updateThumbnailPhoto_If_Added(Proizvod proizvod, MultipartFile multipartFile) throws IOException {
        if(multipartFile != null && !multipartFile.isEmpty()){

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String fileNameWithoutExt = FilenameUtils.removeExtension(fileName);
            String extention = FilenameUtils.getExtension(fileName);
            String fileNameFinal = fileNameWithoutExt + "T" + "." + extention;

            proizvod.setThumbnailPhoto(fileNameFinal);
            this.update(proizvod);

            //return "/slikeProizvodi/proizvodId-" + proizvodId + "/" + thumbnailPhoto;
            String uploadDir = "slikeProizvodi/proizvodId-" + proizvod.getProizvodId();
            FileUploadUtil.saveFile(uploadDir, fileNameFinal, multipartFile);

        }

    }

    @Override
    public Optional<Proizvod> findByNaziv(String naziv) {
        return this.proizvodRepository.findByNaziv(naziv);
    }

    @Override
    public Integer getCenaProizvodaSaPopustom(Proizvod proizvod) {
        Integer cena = proizvod.getCena();
        Integer popust = proizvod.getPopust();
        Integer popustVrednost;
        if(popust != 0){
            popustVrednost = cena * popust/100;
            cena = cena - popustVrednost;
        }
        return cena;
    }

    @Override
    public List<Proizvod> findByVrstaProizvodaVrstaProizvodaId(Integer vrstaProizvodaId) {
       return this.proizvodRepository.findByVrstaProizvodaVrstaProizvodaId(vrstaProizvodaId);
    }

    @Override
    public boolean daLiImaNaStanju(Proizvod proizvod) {
        Integer kolicina = proizvod.getKolicina();
        return kolicina > 0;
    }

    @Override
    public List<Integer> napuniBrojUKoliciniDropDown(Proizvod proizvod) {
        Integer kolicina = proizvod.getKolicina();
        List<Integer> listaBrojevaKolicine = new ArrayList<>();

        if(kolicina > 10) {
            for (int i = 1; i <= 10; i++) {
                listaBrojevaKolicine.add(i);
            }
        }
        else {
            for(int i = 1; i <= kolicina; i++){
                listaBrojevaKolicine.add(i);
            }
        }

        return listaBrojevaKolicine;

    }

    @Override
    public void smanjiKolicinuUMagacinu(Proizvod proizvod, Integer kolicina) {
        Integer proizvodKolicina = proizvod.getKolicina();

        proizvodKolicina = proizvodKolicina - kolicina;

        proizvod.setKolicina(proizvodKolicina);
        this.update(proizvod);

    }

    @Override
    public List<Proizvod> findByKeyword(String keyword) {
        return this.proizvodRepository.findByKeyword(keyword);
    }

    @Override
    public List<Proizvod> orderByPriceAscDesc(String orderDirection) {
        if(orderDirection.equals("ASC")){
            return this.proizvodRepository.orderByPriceAsc();
        }
        else {
            return this.proizvodRepository.orderByPriceDesc();
        }


    }


    @Override
    public Page<ProizvodIzvestajDto> getProizvodIzvestajZaMenadzeraPagination(Pageable pageable) {
        Page<ProizvodIzvestajDto> pageIzvestajaProizvoda =  this.proizvodRepository.getProizvodIzvestajZaMenadzeraPagination(pageable);

        //cena sa popustom formula: (p.cena * (1-p.popust/100))
        /*Integer cena;
        Integer popust;
        Integer popustVrednost;
        for(ProizvodIzvestajDto pid : pageIzvestajaProizvoda){
            cena = pid.getCena();
            popust = pid.getPopust();
            if (popust != 0) {
                popustVrednost = cena * popust/100;
                cena -= popustVrednost;
            }
            pid.setCenaSaPopustom(cena);
        }*/

        return pageIzvestajaProizvoda;
    }

    @Override
    public Page<ProizvodIzvestajDto> findPaginatedProizvodIzvestaje(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();



        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.getProizvodIzvestajZaMenadzeraPagination(pageable);
    }


}
