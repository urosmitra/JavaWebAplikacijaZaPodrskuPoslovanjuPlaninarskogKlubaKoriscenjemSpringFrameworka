package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;

import com.urosmitrasinovic61017.planinarski_klub_webapp.config.FileUploadUtil;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.VrstaPutovanja;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.VrstaPutovanjaRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.VrstaPutovanjaService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VrstaPutovanjaServiceImpl implements VrstaPutovanjaService {

    @Autowired
    private VrstaPutovanjaRepository vrstaPutovanjaRep;

    @Override
    public List<VrstaPutovanja> getAllVrstePutovanja() {
        return this.vrstaPutovanjaRep.findAll();
    }

    @Override
    public VrstaPutovanja save(VrstaPutovanja vrstaPutovanja) {
        return this.vrstaPutovanjaRep.save(vrstaPutovanja);
    }

    @Override
    public VrstaPutovanja update(VrstaPutovanja vrstaPutovanja) {
        return this.vrstaPutovanjaRep.save(vrstaPutovanja);
    }

    @Override
    public void deleteById(Integer id) {
        this.vrstaPutovanjaRep.deleteById(id);
    }

    @Override
    public Optional<VrstaPutovanja> findById(Integer id) {
        return this.vrstaPutovanjaRep.findById(id);
    }



    @Override
    public void updateThumbnailPhoto_If_Added(VrstaPutovanja vrstaPutovanja, MultipartFile multipartFile) throws IOException {
        if(multipartFile != null && !multipartFile.isEmpty()){

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String fileNameWithoutExt = FilenameUtils.removeExtension(fileName);
            String extention = FilenameUtils.getExtension(fileName);
            String fileNameFinal = fileNameWithoutExt + "T" + "." + extention;

            vrstaPutovanja.setThumbnailPhoto(fileNameFinal);
            this.update(vrstaPutovanja);

            String uploadDir = "vrstePutovanja/vrstaPutovanjaId-" + vrstaPutovanja.getVrstaPutovanjaId();
            FileUploadUtil.saveFile(uploadDir, fileNameFinal, multipartFile);

        }
    }
}
