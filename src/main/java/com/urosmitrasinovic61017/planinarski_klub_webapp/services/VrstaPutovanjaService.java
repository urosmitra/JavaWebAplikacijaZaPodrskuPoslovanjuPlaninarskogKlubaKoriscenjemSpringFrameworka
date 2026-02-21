package com.urosmitrasinovic61017.planinarski_klub_webapp.services;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.VrstaPutovanja;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface VrstaPutovanjaService {

    List<VrstaPutovanja> getAllVrstePutovanja();

    VrstaPutovanja save(VrstaPutovanja vrstaPutovanja);

    VrstaPutovanja update(VrstaPutovanja vrstaPutovanja);

    void deleteById(Integer id);

    Optional<VrstaPutovanja> findById(Integer id);

    void updateThumbnailPhoto_If_Added(VrstaPutovanja vrstaPutovanja, MultipartFile multipartFile) throws IOException;
}
