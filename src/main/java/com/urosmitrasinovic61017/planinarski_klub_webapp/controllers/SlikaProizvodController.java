package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;


import com.urosmitrasinovic61017.planinarski_klub_webapp.config.FileUploadUtil;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.SlikaProizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ProizvodService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.SlikaProizvodService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.NazivSlikeProizvodaAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class SlikaProizvodController {


    @Autowired
    private SlikaProizvodService slikaProizvodService;

    @Autowired
    private ProizvodService proizvodService;


    @RequestMapping(value = "/admin/proizvod/slikeProizvoda")
    public String showMenadzmentSlikaProizvodaPage(@RequestParam(value = "proizvodId") Integer proizvodId,
                                                   Model model){

        Proizvod proizvod = this.proizvodService.findById(proizvodId).get();

        List<SlikaProizvod> listaSlikaProizvoda = this.slikaProizvodService.getAllSlikeProizvoda(proizvod);

        model.addAttribute("listaSlikaProizvoda", listaSlikaProizvoda);

        model.addAttribute("proizvod", proizvod);

        return "slikaProizvod/allSlikeProizvoda";

    }


    @RequestMapping(value = "/admin/slikaProizvod/addNew", method = RequestMethod.POST)
    public String addNewImageProizvoda(@RequestParam(value = "proizvodId") Integer proizvodId,
                                       @RequestParam(value = "slika") MultipartFile multipartFile,
                                       RedirectAttributes redirectAttributes) throws IOException {

        Proizvod proizvod = this.proizvodService.findById(proizvodId).get();

        SlikaProizvod novaSlikaProizvoda = new SlikaProizvod();

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        novaSlikaProizvoda.setNazivSlike(fileName);

        novaSlikaProizvoda.setProizvod(proizvod);

        redirectAttributes.addAttribute("proizvodId", proizvod.getProizvodId());

        try{
            this.slikaProizvodService.save(novaSlikaProizvoda);
        } catch (NazivSlikeProizvodaAlreadyExists ex){

            redirectAttributes.addFlashAttribute("nazivSlikeProizvodaAlreadyExists", ex.getMessage());
            redirectAttributes.addFlashAttribute("nazivFajla", fileName);
            return "redirect:/admin/proizvod/slikeProizvoda";
        }


        //kada smo sacuvali u bazu, sada treba da ubacimo u file sistem aplikacije
        String uploadDir = "slikeProizvodi/proizvodId-" + proizvod.getProizvodId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/admin/proizvod/slikeProizvoda";


    }



    @RequestMapping(value = "/admin/slikaProizvod/delete")
    public String deleteSlikuProizvoda(@RequestParam(value = "slikaId") Integer slikaProizvodId,
                                       @RequestParam(value = "proizvodId") Integer proizvodId,
                                       RedirectAttributes redirectAttributes) throws IOException{

        Proizvod proizvod = this.proizvodService.findById(proizvodId).get();

        SlikaProizvod slikaProizvodZaBrisanje = this.slikaProizvodService.findById(slikaProizvodId).get();

        //brisanje slike iz file sistema
        String fileName = slikaProizvodZaBrisanje.getNazivSlike();

        String uploadDir = "slikeProizvodi/proizvodId-" + proizvod.getProizvodId();

        FileUploadUtil.deleteSpecificFile(uploadDir, fileName);
        //KRAJ brisanje slike iz file sistema

        this.slikaProizvodService.deleteById(slikaProizvodZaBrisanje.getSlikaProizvodId());



        redirectAttributes.addFlashAttribute("proizvodObrisan", true);

        redirectAttributes.addAttribute("proizvodId", proizvod.getProizvodId());
        return "redirect:/admin/proizvod/slikeProizvoda";
    }









}
